@import com.comsystoreply.labs.chargingstations.app.model.*
@import com.comsystoreply.labs.chargingstations.app.model.util.*
@param Paged<Station> stationsPage

@template.layout.main(
pageTitle = "Charging Stations",
pageDescription = "A list of all the charging stations.",
content = @`
    @template.partials.stationsTable(stationsPage = stationsPage)
`)