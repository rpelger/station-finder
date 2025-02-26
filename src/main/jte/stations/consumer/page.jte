@import com.comsystoreply.labs.stationfinder.domain.model.*
@import com.comsystoreply.labs.stationfinder.domain.model.util.*

@param Paged<Station> stationsPage
@param Geo center


@template.layout.main(
head = @`
<script>
    let map, circle
    let markers = []

    function createMap(lon, lat) {
        map = L.map('map').setView([lat, lon], 15)
        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        }).addTo(map);
        circle = L.circle([lat, lon], {
            color: '#f03',
            opacity: '0.4',
            fillColor: '#f03',
            fillOpacity: '0.2',
            radius: 500,
        }).addTo(map);
        map.zoomControl.setPosition('bottomright');
        map.on("dragend", () => {
            const {lat, lng} = map.getCenter()
            @raw
            htmx.ajax("GET", `/app/stations/near/${lat}/${lng}`, {target: "#stations-table", swap: "outerHTML"})
                .then(() => {
                    updateMarkers(lat, lng)
                    circle.setLatLng([lat, lng])
                })
            @endraw
        })
    }

    function updateMarkers(lon, lat) {
        if (!map) {
            createMap(lon, lat)
        }
        const positions = Array
            .from(document.querySelectorAll("li.station"))
            .map(elem => [elem.dataset.lat, elem.dataset.lon])
        markers.forEach(m => m.remove())
        markers = positions.map(pos => new L.marker(pos))
        markers.forEach(m => m.addTo(map))
    }
</script>`,

page = @`
    <div id="map" class="map"></div>
    <div class="map-menu milky">
        <div id="pagination-info">
            Found ${stationsPage.itemsTotal()}
            @if(stationsPage.itemsTotal() == 1)
                station
            @else
                stations
            @endif
            within 500m
        </div>
        <div class="toggle-list" id="toggle-list">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                 stroke="currentColor" class="size-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M8.25 6.75h12M8.25 12h12m-12 5.25h12M3.75 6.75h.007v.008H3.75V6.75Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0ZM3.75 12h.007v.008H3.75V12Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm-.375 5.25h.007v.008H3.75v-.008Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Z"/>
            </svg>
        </div>
    </div>
    @template.stations.consumer.fragment_list(stationsPage =  stationsPage)
    <div id="station-details" class="station-details milky">
        Test Test
    </div>`,

afterBody=@`
<script>
    updateMarkers(${center.lon()}, ${center.lat()})
    document.addEventListener("htmx:afterSettle", (e) => {
        if (e.detail.target.id === "stations-table") {
            updateMarkers()
        }
    })
    document.getElementById("toggle-list").addEventListener("click", (e) => {

        let classes = document.querySelector(".stations-table").classList
        if (classes.contains("visible")) {
            classes.remove("visible")
        } else {
            classes.add("visible")
        }
    })
</script>`
)