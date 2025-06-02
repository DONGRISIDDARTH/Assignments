
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private List<Weather> weatherList;
    public void setWeatherList(List<Weather> list) {
        this.weatherList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new WeatherViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Weather weather = weatherList.get(position);
        String dateStr = new SimpleDateFormat("EEE, MMM d", Locale.getDefault())
                .format(new Date(weather.timestamp));
        holder.text1.setText(dateStr + ": " + String.format("%.1f°C, %s", weather.temperature, weather.condition));
        holder.text2.setText("Humidity: " + weather.humidity + "%");
        holder.itemView.setOnClickListener(v -> {
            String message = "Date: " + dateStr + "\nTemp: " + String.format("%.1f°C", weather.temperature)
                    + "\nHumidity: " + weather.humidity + "%\nCondition: " + weather.condition;
            Toast.makeText(v.getContext(), message, Toast.LENGTH_LONG).show();
        });
    }
    @Override
    public int getItemCount() {
        return weatherList == null ? 0 : weatherList.size();
    }
    static class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}
