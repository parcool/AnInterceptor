[![](https://jitpack.io/v/parcool/AnInterceptor.svg)](https://jitpack.io/#parcool/AnInterceptor)
# AnInterceptor
**AnInterceptor** is a interceptor of android that help you route to an activity if it need pre-hanlde and get a callback.

# Usage
---
1. Gradle dependency

    // app module gradle:
    ```
    implementation 'com.github.parcool:AnInterceptor:latestRelease'
    ```
    // root gradle
    ```
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
    ```

2. How to use? Show me the code!
* Override the `startActivityForResult` method in your `BaseActivity`.
    ```
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
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
            //return true means handle this, or pass this intercept
            return SPUtils.getInstance().getBoolean("login", false);
        }
    
        @Override
        public void handle() {
            //if logout we need startActivity to target activity
            activityRef.get().startActivity(new Intent(activityRef.get(), NeedLogonActivity.class));
        }
    }
    ```
* New `LoginActivity` class.
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
* New `NeedLogonActivity` which need logon before. Add `Interceptor annotation` on this `Activity`.
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
* OK, Now we can handle all request(`startActivity`) which to `TargetActivity`. Such as:

    ```
    btn_to_need_logon_activity.setOnClickListener {
        startActivity(new Intent(this, NeedLogonActivity.class));
    }
    ```
3. What it takes for me? ---Time!
* We don't need this code any more.

    ```
    boolean isLogon = SPUtils.getInstance().getBoolean("login", false);
    if (isLogon) {
        startActivity(new Intent(this, NeedLogonActivity.class));
    } else {
        startActivity(new Intent(this, LoginActivity.class));
    }
    ```

* If user login success it will be auto intent to `NeedLogonActivity`

    See gif:
    ![gif](https://github.com/parcool/AnInterceptor/raw/master/gif.gif){:height="50%" width="50%"}

* More than one intercetor?

    See [example](https://github.com/parcool/AnInterceptor/tree/master/example)
# Important!
This lib is not stable, Don't use it to production environment.I'm very glad to you to make PR to improve it!