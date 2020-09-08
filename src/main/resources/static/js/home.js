/*
 * JavaScript file for the application to demonstrate
 * using the API
 */

// Create the namespace instance
let ns = {};

// Create the model instance
ns.model = (function() {
    'use strict';

    let $event_pump = $('body');

    // Return the API
    return {
        'read': function() {
            let ajax_options = {
                type: 'GET',
                data: {length: 50000},
                url: 'api/tickers',
                accepts: 'application/json',
                dataType: 'json'
            };
            $.ajax(ajax_options)
            .done(function(data) {
                $event_pump.trigger('model_read_success', [data]);
            })
            .fail(function(xhr, textStatus, errorThrown) {
                $event_pump.trigger('model_error', [xhr, textStatus, errorThrown]);
            })
        },
        create: function(Symbol, Security, SECFilings, HeadquartersLocation, GICSSubIndustry, GICSSector, Founded, DateFirstAdded, CIK ) {
            let ajax_options = {
                type: 'POST',
                url: 'api/tickers',
                accepts: 'application/json',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify({
                    'Symbol' : Symbol,
                    'Security' : Security,
                    'SECFilings' : SECFilings,
                    'HeadquartersLocation' : HeadquartersLocation,
                    'GICSSubIndustry' : GICSSubIndustry,
                    'GICSSector' : GICSSector,
                    'Founded' : Founded,
                    'DateFirstAdded' : DateFirstAdded,
                    'CIK' : CIK
                })
            };
            $.ajax(ajax_options)
            .done(function(data) {
                $event_pump.trigger('model_create_success', [data]);
            })
            .fail(function(xhr, textStatus, errorThrown) {
                $event_pump.trigger('model_error', [xhr, textStatus, errorThrown]);
            })
        },
        update: function(Symbol, Security, SECFilings, HeadquartersLocation, GICSSubIndustry, GICSSector, Founded, DateFirstAdded, CIK ) {
            let ajax_options = {
                type: 'PATCH',
                url: 'api/tickers',
                accepts: 'application/json',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify({
                    'Symbol' : Symbol,
                    'Security' : Security,
                    'SECFilings' : SECFilings,
                    'HeadquartersLocation' : HeadquartersLocation,
                    'GICSSubIndustry' : GICSSubIndustry,
                    'GICSSector' : GICSSector,
                    'Founded' : Founded,
                    'DateFirstAdded' : DateFirstAdded,
                    'CIK' : CIK
                })
            };
            $.ajax(ajax_options)
            .done(function(data) {
                $event_pump.trigger('model_update_success', [data]);
            })
            .fail(function(xhr, textStatus, errorThrown) {
                $event_pump.trigger('model_error', [xhr, textStatus, errorThrown]);
            })
        }
    };
}());

// Create the view instance
ns.view = (function() {
    'use strict';

    let 
    $Symbol                   = $('#Symbol'),
    $Security                 = $('#Security'),
    $SECFilings               = $('#SECFilings'),
    $HeadquartersLocation     = $('#HeadquartersLocation'),
    $GICSSubIndustry          = $('#GICSSubIndustry'),
    $GICSSector               = $('#GICSSector'),
    $Founded                  = $('#Founded'),
    $DateFirstAdded           = $('#DateFirstAdded'),
    $CIK                      = $('#CIK');

    // return the API
    return {
        reset: function() {
            $Symbol.val('');
            $Security.val('').focus();
            $SECFilings.val('');
            $HeadquartersLocation.val('');
            $GICSSubIndustry.val('');
            $GICSSector.val('');
            $Founded.val('');
            $DateFirstAdded.val('');
            $CIK.val('');
        },
        update_editor: function(Symbol, Security, SECFilings, 
                                HeadquartersLocation, GICSSubIndustry, 
                                GICSSector, Founded, DateFirstAdded, CIK) 
        {
            $Symbol.val(Symbol);
            $Security.val(Security).focus();
            $SECFilings.val(SECFilings);
            $HeadquartersLocation.val(HeadquartersLocation);
            $GICSSubIndustry.val(GICSSubIndustry);
            $GICSSector.val(GICSSector);
            $Founded.val(Founded);
            $DateFirstAdded.val(DateFirstAdded);
            $CIK.val(CIK);
        },
        build_table: function(tickers) {

            let rows = ''

            // clear the table
            $('.tickers table > tbody').empty();

            // did we get a tickers array?
            if (tickers) {
                for (let i=0, l=tickers.length; i < l; i++) {
                    rows += `<tr> 
                                    <td class="leftIndentedCell">${tickers[i].Symbol}</td>
                                    <td class="leftIndentedCell">${tickers[i].Security}</td>
                                    <!-- td class="leftIndentedCell">${tickers[i].SECFilings}</td -->
                                    <td class="leftIndentedCell">${tickers[i].HeadquartersLocation}</td>
                                    <td class="leftIndentedCell">${tickers[i].GICSSubIndustry}</td>
                                    <td class="leftIndentedCell">${tickers[i].GICSSector}</td>
                                    <td class="leftIndentedCell">${tickers[i].Founded}</td>
                                    <td class="leftIndentedCell">${tickers[i].DateFirstAdded}</td>
                                    <td class="leftIndentedCell">${tickers[i].CIK}</td>
                            </tr>`;
                }
                $('table > tbody').append(rows);
            }

            // Pagination and Search Capabilities
            $(document).ready( function () {
                $('#data_table').DataTable();
            } );

            
            
        },
        error: function(error_msg) {
            $('.error')
                .text(error_msg)
                .css('visibility', 'visible');
            setTimeout(function() {
                $('.error').css('visibility', 'hidden');
            }, 3000)
        }
    };
}());

// Create the controller
ns.controller = (function(m, v) {
    'use strict';

    let model = m,
        view = v,
        $event_pump = $('body'),
        $Symbol                   = $('#Symbol'),
        $Security                 = $('#Security'),
        $SECFilings               = $('#SECFilings'),
        $HeadquartersLocation     = $('#HeadquartersLocation'),
        $GICSSubIndustry          = $('#GICSSubIndustry'),
        $GICSSector               = $('#GICSSector'),
        $Founded                  = $('#Founded'),
        $DateFirstAdded           = $('#DateFirstAdded'),
        $CIK                      = $('#CIK');

    // Get the data from the model after the controller is done initializing
    setTimeout(function() {
        model.read();
    }, 100)

    // Validate input
    function validate(Symbol, Security, SECFilings, HeadquartersLocation, GICSSubIndustry, GICSSector, Founded, DateFirstAdded, CIK) {
        return Symbol                   !== "" && 
            Security                    !== "" &&
            SECFilings                  !== "" && 
            HeadquartersLocation        !== "" &&
            GICSSubIndustry             !== "" && 
            GICSSector                  !== "" &&
            Founded                     !== "" && 
            DateFirstAdded              !== "" &&
            CIK                         !== "" ;
    }       

    // Create our event handlers
    $('#create').click(function(e) {
        let Symbol                   = $Symbol.val(),
            Security                 = $Security.val(),
            SECFilings               = $SECFilings.val(),
            HeadquartersLocation     = $HeadquartersLocation.val(),
            GICSSubIndustry          = $GICSSubIndustry.val(),
            GICSSector               = $GICSSector.val(),
            Founded                  = $Founded.val(),
            DateFirstAdded           = $DateFirstAdded.val(),
            CIK                      = $CIK.val();

        e.preventDefault();

        if (validate(Symbol, Security, SECFilings, HeadquartersLocation, GICSSubIndustry, GICSSector, Founded, DateFirstAdded, CIK)) {
            model.create(Symbol, Security, SECFilings, HeadquartersLocation, GICSSubIndustry, GICSSector, Founded, DateFirstAdded, CIK)
        } else {
            alert('Problem with input data');
        }
    });

    $('#update').click(function(e) {
        let Symbol                   = $Symbol.val(),
            Security                 = $Security.val(),
            SECFilings               = $SECFilings.val(),
            HeadquartersLocation     = $HeadquartersLocation.val(),
            GICSSubIndustry          = $GICSSubIndustry.val(),
            GICSSector               = $GICSSector.val(),
            Founded                  = $Founded.val(),
            DateFirstAdded           = $DateFirstAdded.val(),
            CIK                      = $CIK.val();

        e.preventDefault();

        if (validate(Symbol, Security, SECFilings, HeadquartersLocation, GICSSubIndustry, GICSSector, Founded, DateFirstAdded, CIK)) {
            model.update(Symbol, Security, SECFilings, HeadquartersLocation, GICSSubIndustry, GICSSector, Founded, DateFirstAdded, CIK)
        } else {
            alert('Problem with input data');
        }
        e.preventDefault();
    });

    $('#reset').click(function() {
        view.reset();
    })

    $('table > tbody').on('dblclick', 'tr', function(e) {
        let $target = $(e.target),
            Symbol              ,
            Security            ,
            SECFilings          ,
            HeadquartersLocation,
            GICSSubIndustry     ,
            GICSSector          ,
            Founded             ,
            DateFirstAdded      ,
            CIK                 ;

        Symbol = $target
            .parent()
            .find('td.Symbol')
            .text();

        Security = $target
            .parent()
            .find('td.Security')
            .text();

        SECFilings = $target
            .parent()
            .find('td.SECFilings')
            .text();

        HeadquartersLocation = $target
            .parent()
            .find('td.HeadquartersLocation')
            .text();

        GICSSubIndustry = $target
            .parent()
            .find('td.GICSSubIndustry')
            .text();

        GICSSector = $target
            .parent()
            .find('td.GICSSector')
            .text();

        Founded = $target
            .parent()
            .find('td.Founded')
            .text();

        DateFirstAdded = $target
            .parent()
            .find('td.DateFirstAdded')
            .text();

        CIK = $target
            .parent()
            .find('td.CIK')
            .text();

        view.update_editor(Symbol, Security, SECFilings, HeadquartersLocation, GICSSubIndustry, GICSSector, Founded, DateFirstAdded, CIK);
    });

    // Handle the model events
    $event_pump.on('model_read_success', function(e, data) {
        view.build_table(data);
        view.reset();
    });

    $event_pump.on('model_create_success', function(e, data) {
        model.read();
    });

    $event_pump.on('model_update_success', function(e, data) {
        model.read();
    });

    $event_pump.on('model_delete_success', function(e, data) {
        model.read();
    });

    $event_pump.on('model_error', function(e, xhr, textStatus, errorThrown) {
        let error_msg = textStatus + ': ' + errorThrown + ' - ' + xhr.responseJSON.detail;
        view.error(error_msg);
        console.log(error_msg);
    })
}(ns.model, ns.view));


