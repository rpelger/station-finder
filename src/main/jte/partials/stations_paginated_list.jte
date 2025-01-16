@import com.comsystoreply.labs.chargingstations.app.model.Station
@import com.comsystoreply.labs.chargingstations.app.model.util.*
@param Paged<Station> stationsPage



<!-- Pagination Top -->
<nav class="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
    <a href="?page=${Math.max(stationsPage.currentPage() - 1, 1)}"
       hx-get="?page=${Math.max(stationsPage.currentPage() - 1, 1)}"
       hx-target="#stations-table"
       hx-push-url="true"
       class="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">
        <span class="sr-only">Previous</span>
        <svg class="size-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true"
             data-slot="icon">
            <path fill-rule="evenodd"
                  d="M11.78 5.22a.75.75 0 0 1 0 1.06L8.06 10l3.72 3.72a.75.75 0 1 1-1.06 1.06l-4.25-4.25a.75.75 0 0 1 0-1.06l4.25-4.25a.75.75 0 0 1 1.06 0Z"
                  clip-rule="evenodd"/>
        </svg>
    </a>
    <div class="w-16 p-2 text-center text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300">
        <span>${stationsPage.currentPage()}</span>
    </div>
    <a href="?page=${Math.min(stationsPage.currentPage() + 1, stationsPage.totalPages())}"
       hx-get="?page=${Math.min(stationsPage.currentPage() + 1, stationsPage.totalPages())}"
       hx-target="#stations-table"
       hx-push-url="true"
       class="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">
        <span class="sr-only">Next</span>
        <svg class="size-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true"
             data-slot="icon">
            <path fill-rule="evenodd"
                  d="M8.22 5.22a.75.75 0 0 1 1.06 0l4.25 4.25a.75.75 0 0 1 0 1.06l-4.25 4.25a.75.75 0 0 1-1.06-1.06L11.94 10 8.22 6.28a.75.75 0 0 1 0-1.06Z"
                  clip-rule="evenodd"/>
        </svg>
    </a>
</nav>

<!-- Stations List -->
<div class="mt-2 flow-root">
    <div class="overflow-x-auto py-2 align-middle">
        <ul class="divide-y divide-gray-300">
            @for(Station station: stationsPage.items())
                <li id="station-${station.id().value()}" class="py-2 px-0 text-sm text-gray-800 font-medium"
                    onclick='setSelectedStation("${station.id().value()}", map)'>
                    <div class="truncate">${station.operator()}</div>
                    <div class="truncate text-gray-500">
                        ${station.location().address().street()}
                        ${station.location().address().houseNumber()},
                        ${station.location().address().zipCode()}
                        ${station.location().address().city()}
                    </div>
                </li>
            @endfor
        </ul>
    </div>
</div>




<script>
    stations = [
        @for(Station beStation: stationsPage.items())
        {
            id: "${beStation.id().value()}",
            station: {
                name: "${beStation.operator()}",
                address: {
                    street: "${beStation.location().address().street()}",
                    houseNr: "${beStation.location().address().houseNumber()}",
                    zip: "${beStation.location().address().zipCode()}",
                    city: "${beStation.location().address().city()}"
                },
                geo: {
                    lat: "${beStation.location().geo().lat()}",
                    lon: "${beStation.location().geo().lon()}"
                }
            }
        },
        @endfor
    ]

    selected = stations.at(0)
    markers = createMarkers(stations.map(s => s.station))

    if (map) {
        markers.forEach(m => m.addTo(map))
        setSelectedStation(selected.id, map)
    }

</script>
