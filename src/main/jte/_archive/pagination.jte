@import com.comsystoreply.labs.stationfinder.domain.model.*
@import com.comsystoreply.labs.stationfinder.domain.model.util.*

@param Paged<Station> stationsPage

<!-- Pagination Top -->
<nav aria-label="Pagination">
    <a href="#"
       hx-get="?page=${Math.max(stationsPage.currentPage() - 1, 1)}"
       hx-target="#stations-table"
       hx-push-url="true">
        <svg viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
            <path fill-rule="evenodd"
                  d="M11.78 5.22a.75.75 0 0 1 0 1.06L8.06 10l3.72 3.72a.75.75 0 1 1-1.06 1.06l-4.25-4.25a.75.75 0 0 1 0-1.06l4.25-4.25a.75.75 0 0 1 1.06 0Z"
                  clip-rule="evenodd"/>
        </svg>
    </a>
    <div>
        <span>${stationsPage.currentPage()}</span>
    </div>
    <a href="#"
       hx-get="?page=${Math.min(stationsPage.currentPage() + 1, stationsPage.totalPages())}"
       hx-target="#stations-table"
       hx-push-url="true">
        <svg class="size-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
            <path fill-rule="evenodd"
                  d="M8.22 5.22a.75.75 0 0 1 1.06 0l4.25 4.25a.75.75 0 0 1 0 1.06l-4.25 4.25a.75.75 0 0 1-1.06-1.06L11.94 10 8.22 6.28a.75.75 0 0 1 0-1.06Z"
                  clip-rule="evenodd"/>
        </svg>
    </a>
</nav>>