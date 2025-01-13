@import com.comsystoreply.labs.chargingstations.app.model.*
@import java.util.List
@param List<Station> stations

@template.layout.main(
content = @`
    <div class="px-0">
        <div class="mt-2 flow-root">
            <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
                <div class="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
                    <table class="min-w-full divide-y divide-gray-300">
                        <thead>
                        <tr>
                            <th scope="col"
                                class="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-0">Operator
                            </th>
                            <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">Location
                            </th>
                            <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">Chargers
                            </th>
                        </tr>
                        </thead>
                        <tbody class="divide-y divide-gray-200">
                        @for(Station station: stations)
                            <tr>
                                <td class="whitespace-nowrap py-4 pl-4 pr-3 text-sm font-medium text-gray-800 sm:pl-0">${station.operator()}
                                    <br/>&nbsp;
                                </td>
                                <td class="whitespace-nowrap px-3 py-4 text-sm text-gray-600">
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
                                <td class="whitespace-nowrap px-3 py-4 text-sm text-gray-600">
                                    @for(Charger charger : station.chargers())
                                        <div>${charger.type()} (${charger.power()} KW)</div>
                                    @endfor
                                </td>
                            </tr>
                        @endfor
                        <!-- More people... -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
`)