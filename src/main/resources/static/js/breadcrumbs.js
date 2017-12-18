/**
 * @file Responsive Breadcrumbs - Springboot/Thymeleaf
 * @author Vernon Thompson <vernon.thompson@canada.ca>
 * @version cbsl 4.2.1
 * 
 */

var MOBILE_SCREEN_SIZE = 767;
var MIN_RESPONSIVE_SIZE = 4;
var SECOND_INDEX = 1;  
var ELLIPSES = "...";
var ELLIPSES_DATA_STRING = "ellipsesData";
var WB_BC_ID = "#wb-bc";

jQuery(document).on('wb-ready.wb', 'resize', function(event) {
  var list  = $(".breadcrumb").children();
  var size  = list.size();
 
  if ( window.innerWidth <= MOBILE_SCREEN_SIZE && size >= MIN_RESPONSIVE_SIZE) {
      list.each(function( index ) {
         setResponsiveInfo(this, index, size - 2);
      });
  } else {
      list.each(function( index ) {
            restoreEllipsesInfo( this, index, size);
            $(this).show();
      });
  }
});     
 
 // Restore original info that was replaced by the ellipses (...)
 function restoreEllipsesInfo(obj, index) {
      if (index == SECOND_INDEX ) {
          if ($(obj).text() == ELLIPSES) {
                 $(obj).html(($( WB_BC_ID ).data(ELLIPSES_DATA_STRING)).html());
          }
      }
 }
 
 // Save the data that being replaced by the ellipses (...)
 function setEllipses(obj) {
      if ($(obj).text() != ELLIPSES) {
             $ (WB_BC_ID ).data(ELLIPSES_DATA_STRING, $(obj).clone(true));
             $(obj).text(ELLIPSES);
      }
 }
 
 // Set ellipses and hide required Breadcrumb items
 function setResponsiveInfo(obj, index, secondToLastIndex) {
     if (index == SECOND_INDEX ) {
             setEllipses(obj);
     } else {
         if (index > SECOND_INDEX && (index < secondToLastIndex)) {
                 $(obj).hide();
         }
     }
 }