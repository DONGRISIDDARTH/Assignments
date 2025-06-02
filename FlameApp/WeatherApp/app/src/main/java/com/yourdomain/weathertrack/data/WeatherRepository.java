import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.Calendar;
import java.util.List;
public class WeatherRepository {
    private final WeatherDao weatherDao;
    private final WeatherApi weatherApi;
    public WeatherRepository(Application application) {
        WeatherDatabase db = WeatherDatabase.getDatabase(application);
        weatherDao = db.weatherDao();
        weatherApi = new WeatherApi();
    }
    public LiveData<Weather> getLatestWeather() {
        return weatherDao.getLatestWeather();
    }
    public LiveData<List<Weather>> getWeatherPast7Days() {
        Calendar calendar = Calendar.getInstance();
        long end = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        long start = calendar.getTimeInMillis();

        return weatherDao.getWeatherBetween(start, end);
    }
    public void fetchAndSaveWeather(final Callback callback) {
        weatherApi.fetchWeather(new WeatherApi.Callback() {
            @Override
            public void onSuccess(Weather weather) {
                WeatherDatabase.databaseWriteExecutor.execute(() -> {
                    weatherDao.insert(weather);
                    callback.onSuccess(weather);
                });
            }
            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }
    public interface Callback {
        void onSuccess(Weather weather);
        void onFailure(String errorMessage);
    }
}
