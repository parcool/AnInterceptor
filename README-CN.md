[![](https://jitpack.io/v/parcool/AnInterceptor.svg)](https://jitpack.io/#parcool/AnInterceptor)
# AnInterceptor
**AnInterceptor**是一个带回调处理路由请求的拦截库

# English README
[README.md](https://github.com/parcool/AnInterceptor/blob/master/README.md)

# 集成
---
1. Gradle依赖

    ```groovy
    // app模块gradle依赖
    implementation 'com.github.parcool:AnInterceptor:latestRelease'
    ```

    ```groovy
    // 根目录gradle依赖
    allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
    ```

2. 怎么用？请上示例！
* 重新`BaseActivity`的`startActivityForResult`方法。
    ```
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (intent != null) {
            if (options != null && options.getBoolean(AnConstant.REQUIRE_BREAK, false)) {
                super.startActivityForResult(intent, requestCode, options);
            }
            AnInterceptor.startActivityForResult(this, intent, requestCode, options);
        }
    }
    ```
* 新建`LoginInterceptor`处理登录的拦截器.
    ```
    public class LoginInterceptor implements HandlerInterceptor {
    
        WeakReference<Activity> activityRef;
    
        public LoginInterceptor(Activity activity) {
            activityRef = new WeakReference<>(activity);
        }
    
        @Override
        public boolean preHandle() {
            //返回false：拦截当前请求，返回true：忽略当前请求
            return SPUtils.getInstance().getBoolean("login", false);
        }
    
        @Override
        public void handle() {
            //如果没有登录，那么跳转到LoginActivity
            activityRef.get().startActivity(new Intent(activityRef.get(), LoginActivity.class));
        }
    }
    ```
* 新建`LoginActivity`.
    ```
    ……
    btnLogin.setOnClickListener {
        //模拟登录
        SPUtils.getInstance().put("login", true)
        finish()
        //在完成以上代码后，调用下面这个方法
        AnInterceptor.trigger()    
    }
    ……
    ```
* 新建`NeedLogonActivity`然后再在上面添加`Interceptor annotation`注解.
    ```
    @Interceptor(interceptors = {LoginInterceptor.class})
    public class NeedLogonActivity extends BaseActivity {
    
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ……
        }
    }    
    ```
* 现在可以调用(`startActivity`)到`TargetActivity`. 比如:

    ```
    btn_to_need_logon_activity.setOnClickListener {
        //Tips: this code must in SubClass which extends BaseActivity    
        startActivity(new Intent(this, NeedLogonActivity.class));
    }
    ```
# 它有什么作用？
* 不再需要写如下代码.

    ```
    boolean isLogon = SPUtils.getInstance().getBoolean("login", false);
    if (isLogon) {
        startActivity(new Intent(this, NeedLogonActivity.class));
    } else {
        startActivity(new Intent(this, LoginActivity.class));
    }
    ```
* 多个interceptor?

    点击 [example](https://github.com/parcool/AnInterceptor/tree/master/example)

* 如果用户登录成功，它会自动跳转到`NeedLogonActivity`

    See gif:
    ![gif](https://github.com/parcool/AnInterceptor/raw/master/gif.gif)


# 重要!
这个库目前还不稳定，请不要用于生产环境。当然，如果你能提PR那太棒了！👍