@import com.comsystoreply.labs.chargingstations.app.model.*
@import com.comsystoreply.labs.chargingstations.app.model.util.*
@param Paged<Station> stationsPage

<div class="px-0" id="stations-table">
    <div>
        <nav class="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
            <a href="?page=${Math.max(stationsPage.currentPage() - 1, 1)}"
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
    </div>
    <div class="mt-2 flow-root">
        <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
            <div class="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
                <table class="min-w-full divide-y divide-gray-300">
                    <thead>
                    <tr>
                        <th scope="col"
                            class="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-0 w-1/4">
                            Operator
                        </th>
                        <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">Location
                        </th>
                    </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                    @for(com.comsystoreply.labs.chargingstations.app.model.Station station: stationsPage.items())
                        <tr>
                            <td class="whitespace-nowrap text-wrap py-4 pl-4 pr-3 text-sm font-medium text-gray-800 sm:pl-0 align-top">${station.operator()}</td>
                            <td class="whitespace-nowrap text-wrap px-3 py-4 text-sm text-gray-600">
                                ${station.location().address().street()}
                                ${station.location().address().houseNumber()},
                                ${station.location().address().zipCode()}
                                ${station.location().address().city()}
                                (${station.location().address().state()})
                                <br>
                                <span class="text-gray-400">
                                        ${station.location().geo().lat()},
                                        ${station.location().geo().lon()}
                                    </span>
                            </td>
                        </tr>
                    @endfor
                    <!-- More people... -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="flex items-center justify-between border-t border-gray-200 bg-white py-3">
        <div class="flex flex-1 justify-between sm:hidden">
            <a href="#"
               class="relative inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50">Previous</a>
            <a href="#"
               class="relative ml-3 inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50">Next</a>
        </div>
        <div class="hidden sm:flex sm:flex-1 sm:items-center sm:justify-between">
            <div>
                <p class="text-sm text-gray-700">
                    Showing
                    <span class="font-medium">${stationsPage.currentPageStart()}</span>
                    to
                    <span class="font-medium">${stationsPage.currentPageEnd()}</span>
                    of
                    <span class="font-medium">${stationsPage.itemsTotal()}</span>
                    results
                </p>
            </div>
            <div>
                <nav class="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
                    <a href="?page=${Math.max(stationsPage.currentPage() - 1, 1)}"
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
            </div>
        </div>
    </div>

</div>