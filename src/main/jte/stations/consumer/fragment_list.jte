@import com.comsystoreply.labs.stationfinder.domain.model.*
@import com.comsystoreply.labs.stationfinder.domain.model.util.Paged
@import gg.jte.support.*
@param Paged<Station> stationsPage

<!-- Stations List -->
!{var hidden = stationsPage.items().isEmpty() ? "hidden" : "";}
<div id="stations-table" class="stations-table milky ${hidden}">
    <div>
        <ul>
            @for(var entry: ForSupport.of(stationsPage.items()))
                <li class="station" id="station-${entry.get().id().value()}"
                    tabindex="${entry.getIndex()+1}"
                    data-lat="${entry.get().location().geo().lat()}"
                    data-lon="${entry.get().location().geo().lon()}"
                    hx-get="/app/stations/${entry.get().id().value()}"
                    hx-target="#station-details"
                    hx-swap="outerHTML"
                >

                    <div class="station-operator">${entry.get().operator()}</div>
                    <div class="station-address">
                        ${entry.get().location().address().street()}
                        ${entry.get().location().address().houseNumber()},
                        ${entry.get().location().address().zipCode()}
                        ${entry.get().location().address().city()}
                    </div>

                </li>
            @endfor
        </ul>
    </div>
    <div hx-swap-oob="true" id="pagination-info" class="pagination-info">
        Found ${stationsPage.itemsTotal()}
        @if(stationsPage.itemsTotal() == 1)
            station
        @else
            stations
        @endif
        within 500m
    </div>
</div>
