package ddiehl.android.metrics;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.TextView;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_NORMAL;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_SMALL;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_UNDEFINED;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    showMetrics();
  }

  void showMetrics() {
    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    Display display = getWindowManager().getDefaultDisplay();

    TextView tvAndroidVersion = (TextView) findViewById(R.id.android_version);
    tvAndroidVersion.setText(getAndroidVersionString());

    TextView tvSize = (TextView) findViewById(R.id.size);
    tvSize.setText(getScreenSize(getResources().getConfiguration()));

    TextView tvDensityBucket = (TextView) findViewById(R.id.density_bucket);
    tvDensityBucket.setText(getDensityBucket(metrics));

    TextView tvWidth = (TextView) findViewById(R.id.width_px);
    tvWidth.setText(String.valueOf(getWidthPx(display)));

    TextView tvHeight = (TextView) findViewById(R.id.height_px);
    tvHeight.setText(String.valueOf(getHeightPx(display)));

    TextView tvScaledDensity = (TextView) findViewById(R.id.scaled_density);
    tvScaledDensity.setText(String.valueOf(metrics.scaledDensity));

    TextView tvDensityDpi = (TextView) findViewById(R.id.density_dpi);
    tvDensityDpi.setText(String.valueOf(metrics.densityDpi));

    TextView tvXDpi = (TextView) findViewById(R.id.xdpi);
    tvXDpi.setText(String.valueOf(metrics.xdpi));

    TextView tvYDpi = (TextView) findViewById(R.id.ydpi);
    tvYDpi.setText(String.valueOf(metrics.ydpi));
  }

  String getAndroidVersionString() {
    return String.format("%s (API %s)", Build.VERSION.RELEASE, Build.VERSION.SDK_INT);
  }

  String getScreenSize(final @NonNull Configuration config) {
    switch (config.screenLayout & SCREENLAYOUT_SIZE_MASK) {
      case SCREENLAYOUT_SIZE_SMALL: // 0x1
        return "small";
      case SCREENLAYOUT_SIZE_NORMAL: // 0x2
        return "normal";
      case SCREENLAYOUT_SIZE_LARGE: // 0x3
        return "large";
      case SCREENLAYOUT_SIZE_XLARGE: // 0x4
        return "xlarge";
      case SCREENLAYOUT_SIZE_UNDEFINED:
      default:
        return "undefined";
    }
  }

  String getDensityBucket(final @NonNull DisplayMetrics metrics) {
    switch (metrics.densityDpi) {
      case 120: return "ldpi";
      case 160: return "mdpi";
      case 213: return "tvdpi";
      case 240: return "hdpi";
      case 320: return "xhdpi";
      case 480: return "xxhdpi";
      case 640: return "xxxhdpi";
      default: return String.valueOf(metrics.densityDpi);
    }
  }

  int getWidthPx(final @NonNull Display display) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      Point size = new Point();
      display.getSize(size);
      return size.x;
    } else {
      //noinspection deprecation
      return display.getWidth();
    }
  }

  int getHeightPx(final @NonNull Display display) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      Point size = new Point();
      display.getSize(size);
      return size.y;
    } else {
      //noinspection deprecation
      return display.getHeight();
    }
  }
}
