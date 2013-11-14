// This is a manifest file that'll be compiled into application.js, which will include all the files
// listed below.
//
// Any JavaScript/Coffee file within this directory, lib/assets/javascripts, vendor/assets/javascripts,
// or vendor/assets/javascripts of plugins, if any, can be referenced here using a relative path.
//
// It's not advisable to add code directly here, but if you do, it'll appear at the bottom of the
// the compiled file.
//
// WARNING: THE FIRST BLANK LINE MARKS THE END OF WHAT'S TO BE PROCESSED, ANY BLANK LINE SHOULD
// GO AFTER THE REQUIRES BELOW.
//
//= require jquery
//= require jquery_ujs
//= require_tree .
function zerofill(number, length) {
    // Setup
    var result = number.toString();
    var pad = length - result.length;

    while (pad > 0) {
        result = '0' + result;
        pad--;
    }
    return result;
}

$(function () {
    var currentDate = new Date();
    var dateFormat = currentDate.format('dd/mm/yy');
    var hourFormat = zerofill(currentDate.getHours(), 2) + ":" + zerofill((currentDate.getMinutes() - (currentDate.getMinutes() % 10)), 2);
    var defaultVal = dateFormat + " " + hourFormat;
    $('.date_time').each(function () {
        var $this = $(this);
        if ($this.val() == '') $this.val(defaultVal);
    }).datetimepicker({
            dateFormat: 'dd/mm/y',
            timeFormat: 'HH:mm',
            stepHour: 1,
            stepMinute: 10
        });
});
