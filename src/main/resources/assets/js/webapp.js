let renderer = (function () {
    return {
        errorField: function (elm) {
            elm.css('border-color', 'red');
        },
        cleanField: function (elm) {
            elm.css('border-color', 'black');
        },
        greetResult: function (body) {
            $('#result').html(body);
        },
        showError: function(errorText) {
            let q = $.Deferred();
            let notificationsElm = $('#notifications');
            notificationsElm.addClass('error_msg');
            let errorElm = $('<span>').html('Server failure: ' + errorText);
            notificationsElm.append(errorElm);
            notificationsElm.show();

            notificationsElm.fadeTo(5000, 500).slideUp(2500, function () {
                notificationsElm.slideUp(2500);
                notificationsElm.removeClass();
                notificationsElm.html('');
                q.resolve();
            });
            return q.promise();
        }
    }
})();
let Main = (function () {
    let submit = function (evt) {
        evt.preventDefault();
        let submitBtnElm = $('#form_submit');
        submitBtnElm.prop('disabled', true);
        let nameElm = $('#name');
        let name = nameElm.val();
        if(name && name.length > 0) {
            renderer.cleanField(nameElm);
            $.get({
                cache: false,
                url:encodeURI('/api/resource?name=' + name)
            }).done(function (res) {
                renderer.greetResult(res.body);
                submitBtnElm.prop('disabled', false);
            }).fail(function (jqhxr, errorText, type) {
                renderer.showError(errorText).done(function() {
                    submitBtnElm.prop('disabled', false);
                });
            })
        } else {
            renderer.errorField(nameElm);
            submitBtnElm.prop('disabled', false);
        }
    };

    return {
        submit: submit
    }
})();

$(function () {
    $('#greet_form').submit(Main.submit);
    console.log('web ready....');
});