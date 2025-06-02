import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
public class WeatherViewModel extends AndroidViewModel {
    private final WeatherRepository repository;
    private final LiveData<List<Weather>> weeklyWeather;
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public WeatherViewModel(@NonNull Application application) {
        super(application);
        repository = new WeatherRepository(application);
        weeklyWeather = repository.getWeatherPast7Days();
    }
    public LiveData<List<Weather>> getWeeklyWeather() {
        return weeklyWeather;
    }
    public LiveData<String> getError() {
        return error;
    }
    public LiveData<Boolean> getLoading() {
        return loading;
    }
    public void refreshWeather() {
        loading.setValue(true);
        repository.fetchAndSaveWeather(new WeatherRepository.Callback() {
            @Override
            public void onSuccess(Weather weather) {
                loading.postValue(false);
                error.postValue(null);
            }
            @Override
            public void onFailure(String errorMessage) {
                loading.postValue(false);
                error.postValue(errorMessage);
            }
        });
    }
}
