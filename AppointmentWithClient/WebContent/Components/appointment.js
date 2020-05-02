/**
 * 
 */

 $(function()
  {
  $("#datepicker").datepicker(
  {
  showOn:"both",
  buttonImage:"image.jpg",
  dateFormat:"yy-mm-dd",
  buttonImageOnly:false,
  minDate:+0, //you do not want to show previous date.
  maxDate:+0   // you do not want to show next day.
  });
  });
