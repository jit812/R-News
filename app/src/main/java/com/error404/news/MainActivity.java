package com.error404.news;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;

import com.error404.news.api.ApiClient;
import com.error404.news.api.ApiInterface;
import com.error404.news.models.Article;
import com.error404.news.models.News;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public final String [] Clist = new String[]{
            "Abu" ,"Adoni" ,"Agartala" ,"Agra" ,"Ahmadabad" ,"Ahmadnagar" ,"Aizawl" ,"Ajmer" ,"Akola" ,"Alappuzha" ,"Aligarh" ,"Alipore" ,"Alipur Duar" ,"Almora" ,"Alwar" ,"Amaravati" ,"Ambala" ,"Ambikapur" ,"Amer" ,"Amravati" ,"Amreli" ,"Amritsar" ,"Amroha" ,"Anantapur" ,"Anantnag" ,"Ara" ,"Arcot" ,"Asansol" ,"Aurangabad" ,"Ayodhya" ,"Azamgarh" ,"Badagara" ,"Badami" ,"Baharampur" ,"Bahraich" ,"Balaghat" ,"Balangir" ,"Baleshwar" ,"Ballari" ,"Ballia" ,"Bally" ,"Balurghat" ,"Banda" ,"Bangalore" ,"Bankura" ,"Bara Banki" ,"Baramula" ,"Baranagar" ,"Barasat" ,"Bareilly" ,"Baripada" ,"Barmer" ,"Barrackpore" ,"Baruni" ,"Barwani" ,"Basirhat" ,"Basti" ,"Batala" ,"Beawar" ,"Begusarai" ,"Belgavi" ,"Bettiah" ,"Betul" ,"Bhadravati" ,"Bhagalpur" ,"Bhandara" ,"Bharatpur" ,"Bharhut" ,"Bharuch" ,"Bhatpara" ,"Bhavnagar" ,"Bhilai" ,"Bhilwara" ,"Bhind" ,"Bhiwani" ,"Bhojpur" ,"Bhopal" ,"Bhubaneshwar" ,"Bhuj" ,"Bhusawal" ,"Bid" ,"Bidar" ,"Bihar Sharif" ,"Bijnor" ,"Bikaner" ,"Bilaspur" ,"Bilaspur" ,"Bishnupur" ,"Bithur" ,"Bodh Gaya" ,"Bokaro" ,"Brahmapur" ,"Budaun" ,"Budge Budge" ,"Bulandshahr" ,"Buldana" ,"Bundi" ,"Burdwan" ,"Burhanpur" ,"Buxar" ,"Chaibasa" ,"Chamba" ,"Chandernagore" ,"Chandigarh" ,"Chandigarh" ,"Chandigarh" ,"Chandragiri" ,"Chandrapur" ,"Chapra" ,"Chengalpattu" ,"Chennai" ,"Cherrapunji" ,"Chhatarpur" ,"Chhindwara" ,"Chidambaram" ,"Chikkamagaluru" ,"Chitradurga" ,"Chittaurgarh" ,"Chittoor" ,"Churu" ,"Coimbatore" ,"Cuddalore" ,"Cuttack" ,"Dalhousie" ,"Daman" ,"Damoh" ,"Darbhanga" ,"Darjiling" ,"Datia" ,"Daulatabad" ,"Davangere" ,"Dehra Dun" ,"Dehri" ,"Delhi" ,"Deoghar" ,"Deoria" ,"Dewas" ,"Dhamtari" ,"Dhanbad" ,"Dhar" ,"Dharmapuri" ,"Dharmshala" ,"Dhaulpur" ,"Dhenkanal" ,"Dhuburi" ,"Dhule" ,"Diamond Harbour" ,"Dibrugarh" ,"Dinapur Nizamat" ,"Dindigul" ,"Dispur" ,"Diu" ,"Doda" ,"Dowlaiswaram" ,"Dum Dum" ,"Dumka" ,"Dungarpur" ,"Durg" ,"Durgapur" ,"Dwarka" ,"Eluru" ,"Erode" ,"Etah" ,"Etawah" ,"Faizabad" ,"Faridabad" ,"Faridkot" ,"Farrukhabad-cum-Fatehgarh" ,"Fatehpur Sikri" ,"Fatehpur" ,"Firozpur Jhirka" ,"Firozpur" ,"Gandhinagar" ,"Ganganagar" ,"Gangtok" ,"Gaya" ,"Ghaziabad" ,"Ghazipur" ,"Giridih" ,"Godhra" ,"Gonda" ,"Gorakhpur" ,"Gulmarg" ,"Guna" ,"Guntur" ,"Gurdaspur" ,"Gurgaon" ,"Guwahati" ,"Gwalior" ,"Gyalsing" ,"Hajipur" ,"Halebid" ,"Halisahar" ,"Hamirpur" ,"Hamirpur" ,"Hansi" ,"Hanumangarh" ,"Haora" ,"Hardoi" ,"Haridwar" ,"Hassan" ,"Hathras" ,"Hazaribag" ,"Hisar" ,"Hoshangabad" ,"Hoshiarpur" ,"Hubballi-Dharwad" ,"Hugli" ,"Hyderabad" ,"Idukki" ,"Imphal" ,"Indore" ,"Ingraj Bazar" ,"Itanagar" ,"Itarsi" ,"Jabalpur" ,"Jagdalpur" ,"Jaipur" ,"Jaisalmer" ,"Jalandhar" ,"Jalaun" ,"Jalgaon" ,"Jalor" ,"Jalpaiguri" ,"Jamalpur" ,"Jammu" ,"Jamnagar" ,"Jamshedpur" ,"Jaunpur" ,"Jhabua" ,"Jhalawar" ,"Jhansi" ,"Jharia" ,"Jhunjhunu" ,"Jind" ,"Jodhpur" ,"Jorhat" ,"Junagadh" ,"Kadapa" ,"Kaithal" ,"Kakinada" ,"Kalaburagi" ,"Kalimpong" ,"Kalyan" ,"Kamarhati" ,"Kanchipuram" ,"Kanchrapara" ,"Kandla" ,"Kangra" ,"Kannauj" ,"Kanniyakumari" ,"Kannur" ,"Kanpur" ,"Kapurthala" ,"Karaikal" ,"Karimnagar" ,"Karli" ,"Karnal" ,"Kathua" ,"Katihar" ,"Keonjhar" ,"Khajuraho" ,"Khambhat" ,"Khammam" ,"Khandwa" ,"Kharagpur" ,"Khargon" ,"Kheda" ,"Kishangarh" ,"Koch Bihar" ,"Kochi" ,"Kodaikanal" ,"Kohima" ,"Kolar" ,"Kolhapur" ,"Kolkata" ,"Kollam" ,"Konark" ,"Koraput" ,"Kota" ,"Kottayam" ,"Kozhikode" ,"Krishnanagar" ,"Kullu" ,"Kumbakonam" ,"Kurnool" ,"Kurukshetra" ,"Lachung" ,"Lakhimpur" ,"Lalitpur" ,"Leh" ,"Lucknow" ,"Ludhiana" ,"Lunglei" ,"Machilipatnam" ,"Madgaon" ,"Madhubani" ,"Madikeri" ,"Madurai" ,"Mahabaleshwar" ,"Maharashtra" ,"Mahbubnagar" ,"Mahe" ,"Mahesana" ,"Maheshwar" ,"Mainpuri" ,"Malda" ,"Malegaon" ,"Mamallapuram" ,"Mandi" ,"Mandla" ,"Mandsaur" ,"Mandya" ,"Mangaluru" ,"Mangan" ,"Matheran" ,"Mathura" ,"Mattancheri" ,"Meerut" ,"Merta" ,"Mhow" ,"Midnapore" ,"Mirzapur-Vindhyachal" ,"Mon" ,"Moradabad" ,"Morena" ,"Morvi" ,"Motihari" ,"Mumbai" ,"Munger" ,"Murshidabad" ,"Murwara" ,"Mussoorie" ,"Muzaffarnagar" ,"Muzaffarpur" ,"Mysuru" ,"Nabha" ,"Nadiad" ,"Nagaon" ,"Nagappattinam" ,"Nagarjunakoṇḍa" ,"Nagaur" ,"Nagercoil" ,"Nagpur" ,"Nahan" ,"Nainital" ,"Nanded" ,"Narsimhapur" ,"Narsinghgarh" ,"Narwar" ,"Nashik" ,"Nathdwara" ,"Navadwip" ,"Navsari" ,"Neemuch" ,"New Delhi" ,"Nizamabad" ,"Nowgong" ,"Okha" ,"Orchha" ,"Osmanabad" ,"Palakkad" ,"Palanpur" ,"Palashi" ,"Palayankottai" ,"Pali" ,"Panaji" ,"Pandharpur" ,"Panihati" ,"Panipat" ,"Panna" ,"Paradip" ,"Parbhani" ,"Partapgarh" ,"Patan" ,"Patiala" ,"Patna" ,"Pehowa" ,"Phalodi" ,"Phek" ,"Phulabani" ,"Pilibhit" ,"Pithoragarh" ,"Porbandar" ,"Port Blair" ,"Prayagraj" ,"Puducherry" ,"Pudukkottai" ,"Punch" ,"Pune" ,"Puri" ,"Purnia" ,"Purulia" ,"Pusa" ,"Pushkar" ,"Rae Bareli" ,"Raichur" ,"Raiganj" ,"Raipur" ,"Raisen" ,"Rajahmundry" ,"Rajapalaiyam" ,"Rajauri" ,"Rajgarh" ,"Rajkot" ,"Rajmahal" ,"Rajnandgaon" ,"Ramanathapuram" ,"Rampur" ,"Ranchi" ,"Ratlam" ,"Ratnagiri" ,"Rewa" ,"Rewari" ,"Rohtak" ,"Rupnagar" ,"Sagar" ,"Saharanpur" ,"Saharsa" ,"Salem" ,"Samastipur" ,"Sambalpur" ,"Sambhal" ,"Sangareddi" ,"Sangli" ,"Sangrur" ,"Santipur" ,"Saraikela" ,"Sarangpur" ,"Sasaram" ,"Satara" ,"Satna" ,"Sawai Madhopur" ,"Sehore" ,"Seoni" ,"Sevagram" ,"Shahdol" ,"Shahjahanpur" ,"Shahpura" ,"Shajapur" ,"Shantiniketan" ,"Sheopur" ,"Shillong" ,"Shimla" ,"Shivamogga" ,"Shivpuri" ,"Shravanabelagola" ,"Shrirampur" ,"Shrirangapattana" ,"Sibsagar" ,"Sikar" ,"Silchar" ,"Siliguri" ,"Silvassa" ,"Sirohi" ,"Sirsa" ,"Sitamarhi" ,"Sitapur" ,"Siuri" ,"Siwan" ,"Siwan" ,"Solapur" ,"Sonipat" ,"Srikakulam" ,"Srinagar" ,"Sultanpur" ,"Surat" ,"Surendranagar" ,"Tamluk" ,"Tehri" ,"Tezpur" ,"Thalassery" ,"Thane" ,"Thanjavur" ,"Thiruvananthapuram" ,"Thrissur" ,"Tinsukia" ,"Tinsukia" ,"Tiruchchirappalli" ,"Tirunelveli" ,"Tirupati" ,"Tiruppur" ,"Titagarh" ,"Tonk" ,"Tumkuru" ,"Tuticorin" ,"Udaipur" ,"Udayagiri" ,"Udhagamandalam" ,"Udhampur" ,"Ujjain" ,"Ulhasnagar" ,"Una" ,"Valsad" ,"Varanasi" ,"Vasai-Virar" ,"Vellore" ,"Veraval" ,"Vidisha" ,"Vijayawada" ,"Visakhapatnam" ,"Vizianagaram" ,"Warangal" ,"Wardha" ,"Wokha" ,"Yanam" ,"Yavatmal" ,"Yemmiganur" ,"Zunheboto"
    };

    public static final String API_KEY = "23b4a0252a214ad286eea114c8e48d52";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private Adapter adapter;
    private String TAG = MainActivity.class.getSimpleName();
    private TextView topHeadline;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;
    private String category;
    Dialog myDialog;

    AutoCompleteTextView cityName;
    TextView city;
    TextView country;
    TextView temp;
    TextView descrptn;
    TextView humid;
    TextView press;
    TextView wnd;
    ImageButton searchButton;
    TextView result;
    String cn ="Bangalore";

    private String hereLocation(double lat, double lon) {
        String cityName = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 10);
            if (addresses.size() > 0) {
                for (Address adr : addresses) {
                    if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                        cityName = adr.getLocality();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Issue with permission", Toast.LENGTH_LONG).show();
                    return;
                }
                assert locationManager != null;
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                cn = hereLocation(location.getLatitude(), location.getLongitude());
            } else {
                Toast.makeText(this, "Permission not granted!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        TextView txtclose;
        myDialog.setContentView(R.layout.weather_pop_up);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        searchButton = (ImageButton) myDialog.findViewById(R.id.searchButton);

        cityName = myDialog.findViewById(R.id.cityName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Clist);
        cityName.setAdapter(adapter);

        city = myDialog.findViewById((R.id.city));
        country = myDialog.findViewById(R.id.country);
        temp = myDialog.findViewById(R.id.temp);
        descrptn = myDialog.findViewById(R.id.description);
        humid = myDialog.findViewById(R.id.Hval);
        press = myDialog.findViewById(R.id.Pval);
        wnd = myDialog.findViewById(R.id.Wval);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        Objects.requireNonNull(myDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        String content;
        Weather weather = new Weather();
        try {
            content= weather.execute("https://openweathermap.org/data/2.5/weather?q="+cn+"&appid=439d4b804bc8187953eb36d2a8c26a02").get();
            Log.i("content",content);

            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            String windData = jsonObject.getString("wind");
            String mainWeather = jsonObject.getString("main");

            String name = jsonObject.getString("name");
            city.setText(name);


            String sys = jsonObject.getString("sys");
            JSONObject Sys = new JSONObject(sys);
            String cn = ", "+Sys.getString("country");
            country.setText(cn);

            JSONArray array = new JSONArray(weatherData);

            String description = "";
            String temperature;
            String pressure;
            String humidity;
            String wind;

            for (int i=0; i<array.length(); i++){
                JSONObject weatherPart = array.getJSONObject(i);
                description = weatherPart.getString("description");
            }

            JSONObject windPart = new JSONObject(windData);
            wind = windPart.getString("speed");

            Log.i("Speed",wind);
            wnd.setText(wind+"km/hr");

            JSONObject mainPart = new JSONObject(mainWeather);
            temperature = mainPart.getString("temp");
            pressure = mainPart.getString("pressure");
            humidity = mainPart.getString("humidity");

            Log.i("Temperature",temperature);
            temp.setText(temperature);
            Log.i("Pressure",pressure);
            press.setText(pressure+"mb");
            Log.i("Humidity",humidity);
            humid.setText(humidity+"%");

            descrptn.setText(description);
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cName = cityName.getText().toString();

                String content;
                Weather weather = new Weather();
                try {
                    content= weather.execute("https://openweathermap.org/data/2.5/weather?q="+cName+"&appid=439d4b804bc8187953eb36d2a8c26a02").get();
                    Log.i("content",content);

                    //JSON
                    JSONObject jsonObject = new JSONObject(content);
                    String weatherData = jsonObject.getString("weather");
                    String windData = jsonObject.getString("wind");
                    String mainWeather = jsonObject.getString("main");

                    String name = jsonObject.getString("name");
                    city.setText(name);


                    String sys = jsonObject.getString("sys");
                    JSONObject Sys = new JSONObject(sys);
                    String cn = ", "+Sys.getString("country");
                    country.setText(cn);

                    JSONArray array = new JSONArray(weatherData);

                    String description = "";
                    String temperature;
                    String pressure;
                    String humidity;
                    String wind;

                    for (int i=0; i<array.length(); i++){
                        JSONObject weatherPart = array.getJSONObject(i);
                        description = weatherPart.getString("description");
                    }

                    JSONObject windPart = new JSONObject(windData);
                    wind = windPart.getString("speed");

                    Log.i("Speed",wind);
                    wnd.setText(wind+"km/hr");

                    JSONObject mainPart = new JSONObject(mainWeather);
                    temperature = mainPart.getString("temp");
                    pressure = mainPart.getString("pressure");
                    humidity = mainPart.getString("humidity");

                    Log.i("Temperature",temperature);
                    temp.setText(temperature);
                    Log.i("Pressure",pressure);
                    press.setText(pressure+"mb");
                    Log.i("Humidity",humidity);
                    humid.setText(humidity+"%");

                    descrptn.setText(description);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    class Weather extends AsyncTask<String,Void, String>{ //First string means URL is in String, Void means nothing, Third String means Return type will bw String

        @Override
        protected String doInBackground(String... address) {
            //String....means multiple address can be sent. It acts as an array
            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //Establish connection with address
                connection.connect();

                //retrieve data from url
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                //Retrieve data and return it as String
                int data = isr.read();
                String content ="";
                char ch;
                while( data!=-1) {
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                return content;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDialog = new Dialog(this);

        FloatingActionButton BtWeather = (FloatingActionButton) findViewById(R.id.weather);
        BtWeather.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1000);
        }else {
            LocationManager locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try {
                cn = hereLocation(location.getLatitude(),location.getLongitude());
            }catch (Exception e){
                Toast.makeText(MainActivity.this,"Not Found",Toast.LENGTH_SHORT).show();
            }

        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view_bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem =menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.headlines:
                        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        break;
                    case R.id.categories:
                        Intent intent = new Intent(getApplicationContext(),CategoriesActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        topHeadline = findViewById(R.id.topheadelines);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        onLoadingSwipeRefresh("");

        errorLayout = findViewById(R.id.errorLayout);
        errorImage = findViewById(R.id.errorImage);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        btnRetry = findViewById(R.id.btnRetry);

    }

    public void LoadJson(final String keyword){

        errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String country = Utils.getCountry();
        String language = Utils.getLanguage();

        Call<News> call;

        if (keyword.length() > 0 ){

            call = apiInterface.getNewsSearch(keyword, language, "publishedAt", API_KEY);
        } else {
            call = apiInterface.getNews(country, API_KEY);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){

                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticle();
                    adapter = new Adapter(articles, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    initListener();

                    topHeadline.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);


                } else {

                    topHeadline.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }

                    showErrorMessage(
                            R.drawable.no_result,
                            "No Result",
                            "Please Try Again!\n"+
                                    errorCode);

                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                topHeadline.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                showErrorMessage(
                        R.drawable.oops,
                        "Oops..",
                        "Network failure, Please Try Again\n"+
                                t.toString());
            }
        });

    }

    private void initListener(){

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = view.findViewById(R.id.img);
                Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);

                Article article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img",  article.getUrlToImage());
                intent.putExtra("date",  article.getPublishedAt());
                intent.putExtra("source",  article.getSource().getName());
                intent.putExtra("author",  article.getAuthor());

                Pair<View, String> pair = Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainActivity.this,
                        pair
                );


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent, optionsCompat.toBundle());
                }else {
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Latest news...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){
                    onLoadingSwipeRefresh(query);
                }
                else {
                    Toast.makeText(MainActivity.this, "Type more than two letters!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent, chooser;

        if (id == R.id.feedback){
            intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            String[] to = {"timeline.error404@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.setType("message/rfc822");
            chooser =Intent.createChooser(intent,"Send Feedback");
            startActivity(chooser);
            return true;
        }

        else if (id == R.id.shareApp){
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT,"News");
            String sAux = "\nLet me recommend you this news app\n\n";
            sAux = sAux + "A link will  be put here once this app is available for the public in Google play store\n\nThank you from the team of Error404\n\n";
            intent.putExtra(Intent.EXTRA_TEXT,sAux);
            startActivity(Intent.createChooser(intent,"Share app via:"));
            return true;
        }

        else if( id == R.id.about){
            Intent intentA = new Intent(getApplicationContext(),AboutActivity.class);
            intentA.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(intentA);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        LoadJson("");
    }

    private void onLoadingSwipeRefresh(final String keyword){

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson(keyword);
                    }
                }
        );

    }

    private void showErrorMessage(int imageView, String title, String message){

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadingSwipeRefresh("");
            }
        });

    }


}
