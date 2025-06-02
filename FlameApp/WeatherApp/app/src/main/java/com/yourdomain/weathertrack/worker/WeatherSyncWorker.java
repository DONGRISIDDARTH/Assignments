import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
public class WeatherSyncWorker extends Worker {
    private WeatherRepository repository;
    public WeatherSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repository = new WeatherRepository((Application) context.getApplicationContext());
    }
    @NonNull
    @Override
    public Result doWork() {
        final boolean[] success = {false};
        final Object lock = new Object();
        repository.fetchAndSaveWeather(new WeatherRepository.Callback() {
            @Override
            public void onSuccess(Weather weather) {
                synchronized (lock) {
                    success[0] = true;
                    lock.notify();
                }
            }
            @Override
            public void onFailure(String errorMessage) {
                synchronized (lock) {
                    success[0] = false;
                    lock.notify();
                }
            }
        });
        synchronized (lock) {
            try {
                lock.wait(5000); // wait max 5 sec for callback
            } catch (InterruptedException e) {
                e.printStackTrace();
                return Result.retry();
            }
        }
        return success[0] ? Result.success() : Result.retry();
    }
}


