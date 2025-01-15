@import com.comsystoreply.labs.chargingstations.app.model.Station
@import com.comsystoreply.labs.chargingstations.app.model.util.*
@import org.slf4j.*
@param Paged<Station> stationsPage


<div class="px-0 sm:w-full lg:w-1/3 " id="stations-table">
    <!-- Pagination Top -->
    <nav class="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
        <a href="?page=${Math.max(stationsPage.currentPage() - 1, 1)}"
           hx-get="?page=${Math.max(stationsPage.currentPage() - 1, 1)}"
           hx-target="#stations-table"
           hx-push-url="true"
           hx-swap="outerHTML"
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
           hx-swap="outerHTML"
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
                    <li class="py-2 px-0 text-sm text-gray-800 font-medium">
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
</div>
<div class="mt-2 hidden" id="current-center" hx-swap-oob="innerHTML:#current-center">
    !{var currentCenter = stationsPage.items().get(stationsPage.items().size()/2-1);}
    <h2 class="font-bold">${currentCenter.operator()}</h2>
    <div>
        ${currentCenter.location().address().street()}
        ${currentCenter.location().address().houseNumber()},
        ${currentCenter.location().address().zipCode()}
        ${currentCenter.location().address().city()}
        <br>
        <span class="text-gray-400">${currentCenter.location().geo().lon()} / ${currentCenter.location().geo().lat()}</span>
    </div>
</div>
<script>
    if (window.map && window.currentMarkers) {
        map.flyTo({
            center: [
                ${currentCenter.location().geo().lon()},
                ${currentCenter.location().geo().lat()}
            ],
            essential: true // this animation is considered essential with respect to prefers-reduced-motion
        });

        currentMarkers.forEach(marker => marker.remove())
        currentMarkers = []
        @for(Station s : stationsPage.items())
        currentMarkers.push(
            new maplibregl.Marker().setLngLat([${s.location().geo().lon()}, ${s.location().geo().lat()}])
        )
        @endfor
        currentMarkers.forEach(marker => marker.addTo(map))
    }
</script>



