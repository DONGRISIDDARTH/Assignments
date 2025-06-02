import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
@Dao
public interface WeatherDao {
    @Insert
    void insert(Weather weather);
    @Query("SELECT * FROM weather_table WHERE timestamp >= :start AND timestamp <= :end ORDER BY timestamp ASC")
    LiveData<List<Weather>> getWeatherBetween(long start, long end);
    @Query("SELECT * FROM weather_table ORDER BY timestamp DESC LIMIT 1")
    LiveData<Weather> getLatestWeather();
}
