[![](https://jitpack.io/v/parcool/AnInterceptor.svg)](https://jitpack.io/#parcool/AnInterceptor)
# AnInterceptor
**AnInterceptor** is a interceptor of android that help you intercept a route request with a `Callback`.

# 中文
[README.md](https://github.com/parcool/AnInterceptor/blob/master/README-CN.md)

# Usage
---
1. Gradle dependency

    ```groovy
    // app module gradle
    implementation 'com.github.parcool:AnInterceptor:latestRelease'
    ```

    ```groovy
    // root gradle 
    allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
    ```

2. How to use? Show me the code!
* Override the `startActivityForResult` method in your `BaseActivity`.
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
* New `LoginInterceptor` to intercept the route request.
    ```
    public class LoginInterceptor implements HandlerInterceptor {
    
        WeakReference<Activity> activityRef;
    
        public LoginInterceptor(Activity activity) {
            activityRef = new WeakReference<>(activity);
        }
    
        @Override
        public boolean preHandle() {
            //return false means handle this, or pass this intercept
            return SPUtils.getInstance().getBoolean("login", false);
        }
    
        @Override
        public void handle() {
            //if logout we need startActivity to target activity
            activityRef.get().startActivity(new Intent(activityRef.get(), LoginActivity.class));
        }
    }
    ```
* Add `AnInterceptor.trigger()` after login success in your `LoginActivity` class.
    ```
    ……
    btnLogin.setOnClickListener {
        //login code such as:  
        SPUtils.getInstance().put("login", true)
        finish()
        //Add this method to completed this interceptor
        AnInterceptor.trigger()    
    }
    ……
    ```
* Add `Interceptor` annotation on `NeedLogonActivity` class which require logon.
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
* OK, Now we can handle all request(`startActivity`) which to `NeedLogonActivity`. Such as:

    ```
    btn_to_need_logon_activity.setOnClickListener {
        //Tips: this code must in SubClass which extends BaseActivity    
        startActivity(new Intent(this, NeedLogonActivity.class));
    }
    ```
# What it takes for me? ---Time!
* We don't need this code any more.

    ```
    boolean isLogon = SPUtils.getInstance().getBoolean("login", false);
    if (isLogon) {
        startActivity(new Intent(this, NeedLogonActivity.class));
    } else {
        startActivity(new Intent(this, LoginActivity.class));
    }
    ```
* More than one interceptor on the same `Activity`?

    See [example](https://github.com/parcool/AnInterceptor/tree/master/example)

* If user login success it will be auto intent to `NeedLogonActivity`

    ![gif](https://github.com/parcool/AnInterceptor/raw/master/gif.gif)


# Important!
This lib is not stable, Don't use it to production environment.I'm very glad for you make PR to improve it!