@import com.comsystoreply.labs.chargingstations.app.model.Station
@import java.util.List
@param List<Station> stations

<html>
<head></head>
<body>
<h1>List all stations</h1>
<ul>
    @for(Station station : stations)
        <li>${station.operator()} - ${station.location().toString()}</li>
    @endfor
</ul>
</body>
</html>