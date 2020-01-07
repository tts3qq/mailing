$(function () {
    let languageSwitcher = $("#locales");
    let currentLanguage = $('#locales').attr('current');

    languageSwitcher.selectpicker('val', currentLanguage);

    $(".contact-form").submit(function () {
        $.ajax({
            type: "POST",
            url: "/send",
            data: $(".contact-form").serialize(),
            success: function (data) {
                let message = $('.alert-container').attr('data-message');
                $('.alert-container').append("<div class=\"alert alert-success\" role=\"alert\">" + message + "</div>");
                $('.alert').delay(5000).fadeOut("slow", function () {
                    $('.alert').prop("disabled", false);
                });
            }
        });
        return false;
    });


    languageSwitcher.change(function () {
        const selectedOption = $('#locales').val();
        if (selectedOption !== '') {
            window.location.replace(window.location.pathname + '?lang=' + selectedOption);
        }
    });
});
