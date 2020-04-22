# 说明

> WeNetJudger主要是用来进行网络类型判断，网络状态监听的框架。

## 写在最前面

> ​	在介绍这个框架之前，先说一下自己在日常开发中遇到的困扰。在客户端中，每次在网络请求时，都要进行手动判断，导致大量的重复工作。并且很多功能都是先进行业务开发，后来才回头细化用户体验方面的细节，比如在用户登录功能时，肯定先会实现登录逻辑，功能好用后才会继续其他用户功能模块开发，但日积月累，逻辑越来越多，需要细化的东西也就越多，网络状态判断就是一个例子。如果每处都是手动添加，判断`if`和`else`会工作量很大，如果在底层请求部分加入，虽然可以一劳永逸，但又觉得在`base`部分加入这种逻辑判断，侵入性太强，功能单一的原则就遭到破坏，所以就想有没有在不打破单一原则，修改成本最低的同时，还能保证功能的加入呢？经过一番思考之后，答案是有的。可能至少对我来讲这个方法应该是一个较为理想的方式了，虽说也要对之前的代码做一些修改，但至少能接受。
>
> ​	受到钩子（Hook）的启发，可不可以在用户调用某个涉及网络请求的方法时，触发钩子，先判断网络状态，只有当状态满足要求时才会继续向下执行。当然钩子只是启发并不是真正的钩子程序。因此我结合Runtime时期的反射机制进行了一个尝试。

## 原理

> ​	大概就是在想要进行网络状态判断的方法上加入注解，这里我用的是`@WeNetJudger`，然后注解处理器会解析到该类下所有加入注解的方法，并进行暂存，然后再通过一个特定方式去调用这个方法，其中这个特定方式就包含了对网络状态的判断和反射执行该方法。

## 使用

> - 先定义好一个Application，然后再其onCreate方法中加入：
>
>   ```java
>   WeNetManager.getDefault().init(this);
>   ```
>
> - 如果想要对网络状态变化做监听
>
>   - 可以先定义一个BaseActivity，并且实现INetChangedNotifier接口，在onNetFound方法实现当有/无网络时触发的UI
>
>   ```java
>   @Override
>       public void onNetFound(boolean isWhat) {
>           if(isWhat){
>               Log.e("---------", "onNetFound: base网络可用" );
>           }else{
>               Log.e("---------", "onNetFound: base网络不可用" );
>           }
>       }
>   ```
>
>   - 在BaseActivity加入注解：
>
>     ```java
>     @WeNetChanger
>     public class BaseActivity extends AppCompatActivity implements INetChangedNotifier
>     ```
>
>     
>
> - 在BaseActivity或者想要对网络状态判断和监听的界面加入：
>
>   ```java
>   WeNetManager.getDefault().registerObserver(this);
>   ```
>
>   以上基本初始化工作已经做完了，当网络变化时，从有网到无网状态时切换时，查看效果。

### 其他使用

- 网络请求前想要先判断网络类型，不低于最小期望值时才进行执行

  > 比如说，MainActivity中想要调用TmpTool的test方法，假设test就是网络请求方法

  `TmpTool.java`

  ```JAVA
  public class TmpTool {
      public TmpTool() {
          WeNetManager.getDefault().registerObserver(this);
      }
  
      @WeNetJudger(limit = NetType.NETWORK_3G, tag = "test")
      public void test(String name,String parentId) {
          Log.e("***********", "test:"+name);
          Log.e("***********", "test:id"+parentId);
      }
  }
  ```

  `MainActivity.java`

  ```java
  Button testBtn_2 = findViewById(R.id.testBtn_2);
  testBtn_1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          WeNetManager.getDefault().invoke(tmpTool,"test","测试名称","测试id");
      }
  });
  ```

  说明一下两个注意点：

  - ```JAVA
    @WeNetJudger(limit = NetType.NETWORK_3G, tag = "test")
    ```

    > @WeNetJudger只适用与方法，不适用类，limit是网络请求最低期望值，只有当网络类型等于或者优于这个状态时才会执行；tag用于标记当前方法的一个别名，尽量不要重复。

  - ```java
    WeNetManager.getDefault().invoke(tmpTool,"test","测试名称","测试id");
    ```

    > 参数1：要调用方法所在的类；
    >
    > 参数2：方法别名，可以与方法名相同，但最好是类名+方法的形式；
    >
    > 参数3与参数4：这里的参数是数组类型，长度不限，完全取决于原有方法的参数列表
    >
    > `反射invoke的时候会根据类型和参数列表去定位方法，所以这两个因素较为重要`

最后，释放资源

```java
@Override
protected void onDestroy() {
    super.onDestroy();
    //注销目标广播
    WeNetManager.getDefault().unRegisterObserver(this);
    //注销所有广播
    WeNetManager.getDefault().unRegisterAllObserver();
}
```

## 写在最后

> Bug肯定是有，测试场景也比较复杂，所以欢迎PR