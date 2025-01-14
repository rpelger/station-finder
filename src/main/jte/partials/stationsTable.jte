@import com.comsystoreply.labs.chargingstations.app.model.Station
@import com.comsystoreply.labs.chargingstations.app.model.util.*
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

    <!-- Station List Footer -->
    <div class="flex items-center justify-between bg-white py-3">
        <div class="hidden sm:flex sm:flex-1 sm:items-center sm:justify-between">
            <!-- Pagination Bottom-->
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
                <span class="relative hidden items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 focus:z-20 focus:outline-offset-0 md:inline-flex">${stationsPage.currentPage()}</span>
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

            <div class="text-sm text-gray-400">
                Showing
                <span class="font-medium">${stationsPage.currentPageStart()}</span>
                to
                <span class="font-medium">${stationsPage.currentPageEnd()}</span>
                of
                <span class="font-medium">${stationsPage.itemsTotal()}</span>
                results
            </div>


        </div>
    </div>

</div>