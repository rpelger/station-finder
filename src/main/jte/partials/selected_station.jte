@import com.comsystoreply.labs.chargingstations.adapters.web.*

@param StationResponse station

<h2 id="station-station_name" class="font-bold">${station.operator()}</h2>
<div>
    <div>
        <span id="station-station_address">
            ${station.location().address().street()} ${station.location().address().houseNumber()},
            ${station.location().address().zipCode()} ${station.location().address().city()}
        </span>
    </div>
    <div>
        <span id="station-station_geo" class="text-gray-400">
            ${station.location().geo().lat()} / ${station.location().geo().lon()}
        </span>
        <ul id="station-station_chargers" class="list-disc list-inside">
            @for(StationResponse.ChargerResponse charger : station.chargers())
                <li>${charger.count()}x ${charger.name()}</li>
            @endfor
        </ul>
    </div>
</div>