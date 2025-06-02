import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private WeatherViewModel viewModel;
    private WeatherAdapter adapter;
    private Button refreshButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshButton = findViewById(R.id.refreshButton);
        RecyclerView recyclerView = findViewById(R.id.weatherRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        viewModel.getWeeklyWeather().observe(this, weathers -> {
            if (weathers != null && !weathers.isEmpty()) {
                adapter.setWeatherList(weathers);
            } else {
                Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getError().observe(this, errorMsg -> {
            if (errorMsg != null) {
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
            }
        });
        viewModel.getLoading().observe(this, loading -> {
            refreshButton.setEnabled(!loading);
            if (loading) {
                Toast.makeText(this, "Fetching weather...", Toast.LENGTH_SHORT).show();
            }
        });
        refreshButton.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                viewModel.refreshWeather();
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        if (connectivityManager == null) return false;
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                 capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                 capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
    }
}
