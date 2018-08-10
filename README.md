# ClockLoadingView
A clock loading view(一个时钟loading view).

[![Api reqeust](https://img.shields.io/badge/api-11+-green.svg)](https://github.com/samlss/ClockLoadingView)  [![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://github.com/samlss/ClockLoadingView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)

<br>

  * [中文](#%E4%B8%AD%E6%96%87)
  * [English](#english)
  * [License](#license)

<br>

![gif1](https://github.com/samlss/ClockLoadingView/blob/master/screenshots/screenshot1.gif)



## 中文

### 使用<br>
在根目录的build.gradle添加这一句代码：
```
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

在app目录下的build.gradle添加依赖使用：
```
dependencies {
    implementation 'com.github.samlss:ClockLoadingView:1.0'
}
```

布局中使用：
```
 <com.iigo.library.ClockLoadingView
            android:layout_marginTop="80dp"
            android:id="@+id/clv_loading2"
            android:layout_width="100dp"
            android:layout_height="100dp"
            app:center_color="@color/yellow"
            app:circle_color="@color/green"
            app:hour_hand_color="@color/orange"
            app:minute_hand_color="@color/red" />
```

<br>

代码中使用：
```
  clockLoadingView.start(); //开始动画
  clockLoadingView.stop(); //结束动画
  
  clockLoadingView.setCircleColor(getResources().getColor(R.color.green)); //设置圆描边的颜色
  clockLoadingView.setCenterColor(getResources().getColor(R.color.yellow)); //设置圆心颜色
  clockLoadingView.setHourHandColor(getResources().getColor(R.color.orange)); //设置时针颜色
  clockLoadingView.setMinHandColor(getResources().getColor(R.color.red)); //设置分针颜色
```

<br>
在Activity结束的时候调用一下接口进行释放：

```
 @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing()){
            clockLoadingView.release();
        }
    }
```

<br>

属性说明：

| 属性        | 说明           |
| ------------- |:-------------:|
| circle_color      | 圆的描边颜色 |
| center_color | 圆心颜色 |
| hour_hand_color | 时针颜色 |
| minute_hand_color | 分针颜色 |

<br>

如果不能满足你的需要，你可以下载源码自行修改。

## English

### Use<br>
Add it in your root build.gradle at the end of repositories：
```
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

Add it in your app build.gradle at the end of repositories:
```
dependencies {
    implementation 'com.github.samlss:ClockLoadingView:1.0'
}
```


in layout.xml：
```
 <com.iigo.library.ClockLoadingView
            android:layout_marginTop="80dp"
            android:id="@+id/clv_loading2"
            android:layout_width="100dp"
            android:layout_height="100dp"
            app:center_color="@color/yellow"
            app:circle_color="@color/green"
            app:hour_hand_color="@color/orange"
            app:minute_hand_color="@color/red" />
```

<br>

in java code：
```
  clockLoadingView.start(); //start animation
  clockLoadingView.stop(); //stop animation
  
  clockLoadingView.setCircleColor(getResources().getColor(R.color.green)); //set the circle stroke color
  clockLoadingView.setCenterColor(getResources().getColor(R.color.yellow)); //set the circle center color
  clockLoadingView.setHourHandColor(getResources().getColor(R.color.orange)); //set the hour hand color
  clockLoadingView.setMinHandColor(getResources().getColor(R.color.red)); //set the minute hand color
```
<br>

Call the below mehod to release when the activity is finishing.：
```
 @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing()){
            clockLoadingView.release();
        }
    }
```

<br>

Attributes description：

| attr        | description  |
| ------------- |:-------------:|
| circle_color      | the circle stroke color |
| center_color | the circle center color |
| hour_hand_color | the hour hand color |
| minute_hand_color | the minute hand color |

If you can not meet your needs, you can download the source code to modify it.

[id]: http://example.com/ "Optional Title Here"

## [LICENSE](https://github.com/samlss/PeasLoadingView/blob/master/LICENSE)
