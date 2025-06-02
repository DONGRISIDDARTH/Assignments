
import android.os.Handler;
import android.os.Looper;
public class WeatherApi {
    public interface Callback {
        void onSuccess(Weather weather);
        void onFailure(String errorMessage);
    }
    public void fetchWeather(final Callback callback) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            try {
                // Mock data
                long timestamp = System.currentTimeMillis();
                double temperature = 15 + Math.random() * 10;  // 15 to 25
                int humidity = 40 + (int)(Math.random() * 40); // 40 to 80%
                String[] conditions = {"Sunny", "Cloudy", "Rainy", "Windy"};
                String condition = conditions[(int) (Math.random() * conditions.length)];

                Weather weather = new Weather(timestamp, temperature, humidity, condition);

                callback.onSuccess(weather);
            } catch (Exception e) {
                callback.onFailure("Failed to fetch weather");
            }
        }, 2000);
    }
}
