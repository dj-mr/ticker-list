/*
 * JavaScript file for the application to demonstrate
 * using the API
 */

// Create the namespace instance
let ns = {};

// Create the model instance
ns.model = (function () {
    'use strict';

    let $event_pump = $('body');

    // Return the API
    return {
        'read': function () {
            let ajax_options = {
                type: 'GET',
                data: {number_of_samples: 1000},
                url: '/tickers',
                accept: 'application/json',
                dataType: 'json'
            };
            $.ajax(ajax_options)
                .done(function (data) {
                    $event_pump.trigger('model_read_success', [data]);
                })
                .fail(function (textStatus, errorThrown, xhr) {
                    $event_pump.trigger('model_error', [xhr, textStatus, errorThrown]);
                })
        }
    };
}());

// Create the view instance
ns.view = (function () {
    'use strict';

    let $Name = $('#Name'),
        $Ticker = $('#Ticker'),
        $CIK = $('#CIK'),
        $Headquarters = $('#Headquarters'),
        $GICSSubIndustry = $('#GICSSubIndustry'),
        $GICSSector = $('#GICSSector'),
        $Incorporation = $('#Incorporation')
    ;

    // return the API
    return {
        reset: function () {
            $Name.val('');
            $Ticker.val('').focus();
            $CIK.val('');
            $Headquarters.val('');
            $GICSSubIndustry.val('');
            $GICSSector.val('');
            $Incorporation.val('');
        },
        build_table: function (tickers) {

            let rows = ''

            // clear the table
            $('.tickers table > tbody').empty();

            // did we get a tickers array?
            if (tickers) {
                for (let i = 0, l = tickers.length; i < l; i++) {
                    let name = `${tickers[i].name}`;
                    let ticker = `${tickers[i].ticker}`;
                    let cik = `${tickers[i].cik}`;
                    let address = ` `;
                    let incorporation = ` `;
                    let industry = ` `;
                    let sicTitle = ` `;

                    if (tickers[i].organizationDetails != null) {
                        address = tickers[i].organizationDetails == null? ` ` : `${tickers[i].organizationDetails.address.replaceAll(',', '<br/>')}`;
                        incorporation = tickers[i].organizationDetails == null? ` ` : `${tickers[i].organizationDetails.stateOfIncorporation}`;

                        if (tickers[i].organizationDetails.sicData != null) {
                            industry = `${tickers[i].organizationDetails.sicData.industry}`;
                            sicTitle = `${tickers[i].organizationDetails.sicData.sicTitle}`;
                        }
                    }

                    rows += `<tr> 
                                    <td class="leftIndentedCell">${name}</td>
                                    <td class="leftIndentedCell">${ticker}</td>
                                    <td class="leftIndentedCell">${cik}</td>
                                    <td class="leftIndentedCell">${address}</td>
                                    <td class="leftIndentedCell">${industry}</td>
                                    <td class="leftIndentedCell">${sicTitle}</td>
                                    <td class="leftIndentedCell">${incorporation}</td>
                            </tr>`;
                }
                $('table > tbody').append(rows);
            }

            // Pagination and Search Capabilities
            $(document).ready(function () {
                $('#data_table').DataTable();
            });


        },
        error: function (error_msg) {
            $('.error')
                .text(error_msg)
                .css('visibility', 'visible');
            setTimeout(function () {
                $('.error').css('visibility', 'hidden');
            }, 3000)
        }
    };
}());

// Create the controller
ns.controller = (function (m, v) {
    'use strict';

    let model = m,
        view = v,
        $event_pump = $('body'),
        $Name = $('#Name'),
        $Ticker = $('#Ticker'),
        $CIK = $('#CIK'),
        $Headquarters = $('#Headquarters'),
        $GICSSubIndustry = $('#GICSSubIndustry'),
        $GICSSector = $('#GICSSector'),
        $Incorporation = $('#Incorporation');

    // Get the data from the model after the controller is done initializing
    setTimeout(function () {
        model.read();
    }, 100)

    $('#reset').click(function () {
        view.reset();
    })

    $('table > tbody').on('dblclick', 'tr', function (e) {
        let $target = $(e.target),
            Name,
            Ticker,
            CIK,
            Headquarters,
            GICSSubIndustry,
            GICSSector,
            Incorporation;

        Name = $target
            .parent()
            .find('td.Name')
            .text();

        Ticker = $target
            .parent()
            .find('td.Ticker')
            .text();

        CIK = $target
            .parent()
            .find('td.CIK')
            .text();

        Headquarters = $target
            .parent()
            .find('td.Headquarters')
            .text();

        GICSSubIndustry = $target
            .parent()
            .find('td.GICSSubIndustry')
            .text();

        GICSSector = $target
            .parent()
            .find('td.GICSSector')
            .text();

        Incorporation = $target
            .parent()
            .find('td.Incorporation')
            .text();

        view.update_editor(Name, Ticker, CIK, Headquarters, GICSSubIndustry, GICSSector, Incorporation);
    });

    // Handle the model events
    $event_pump.on('model_read_success', function (e, data) {
        view.build_table(data);
        view.reset();
    });

    $event_pump.on('model_error', function(e, xhr, textStatus, errorThrown) {
        let error_msg = textStatus
            + ': ' + errorThrown;
        view.error(error_msg);
        console.log(error_msg);
    })
}(ns.model, ns.view));


