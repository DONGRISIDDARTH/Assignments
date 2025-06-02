import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "weather_table")
public class Weather {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public long timestamp;
    public double temperature;
    public int humidity;
    public String condition;
    public Weather(long timestamp, double temperature, int humidity, String condition) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.humidity = humidity;
        this.condition = condition;
    }
}
