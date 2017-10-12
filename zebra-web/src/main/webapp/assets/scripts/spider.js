(function ($)
{
	//全局系统对象
    window['SP'] = {};
    
    SP.checker=function(form){
    	form.trigger("checkInput");
    	if( $(".formErrorContent").css("display")=="block"){return false;}
    	if(form.find('#begin_time').length>0 && form.find('#end_time').length>0){
    		if(new Date(form.find('#begin_time').val()).getTime()-new Date(form.find('#end_time').val()).getTime()>0){
    			 toastr.error('开始时间不能大于结束时间');
    			return false;
    		}
    	}
    	return true;
    };
    

    SP.ajax=function(form,options,check){
    	if(check===undefined){
    		check=true;
    	}
    	var exec=true;
    	if(check){
    		check=SP.checker(form);
    		exec=check;
    	}
    	if(exec){
	    	var p = options || {};
	    	var url = p.url || form.attr("action");
	    	var sendType=form.attr("method") || p.type || 'post';
	    	var pdata=p.data || SP.serializeToJson(form);
	    	
	    	//if( form.find('#submit').length>0 && form.find('#submit').hasClass('custom')){
	    	//	return;
	    	//}
	    	
	    	  $.ajax({
	              cache: false,
	              contentType: 'application/json',
	              async: true,
	              url: url,
	              data: pdata,
	              dataType: 'json', type: sendType,
	              beforeSend: function ()
	              {
	                  if (p.beforeSend){
	                	  p.beforeSend();
	                  }else{
	                  }
	                     
	              },
	              complete: function ()
	              {
	                  if (p.complete){
	                	  p.complete();
	                  }else{
	                  }
	              },
	              success: function (res){
	            	  if (p.success){
	            		  p.success(res);
	            	  }else{
	            		  if(res.result=='success'){
	            			  toastr.success('操作成功');
	            		  }else{
	            			  toastr.error(res.msg,'操作失败');
	            		  }
	            		  if(form.attr("refresh_tabid")){
	            			  window.location.href=form.attr("refresh_tabid")
	            		  }
	            	  }
	              },
	              error: function (res, b)
	              {
	            	  if(p.error){
	            		  p.error(res,b);
	            	  }else{
	            		  console.log(res)
	            		  try{
	            			  var errMsg= eval('('+res.responseText+')');
		            		  toastr.error( errMsg.msg,'操作失败');
	            		  }catch(err){
	            			  toastr.error( "你没有权限进行此操作!",'操作失败');
	            		  }
	            		 
	            	  }
	              }
	    	  });
    	}
    };
    
    SP.loadTableInfo=function(table,options,form){
    	var data;
    	if(form){
    		data=form.serializeArray();
    	}else{
    		data=options.data||{};
    	}
    	var bServerSide;
    	if(options.bServerSide===undefined){
    		bServerSide=true;
    	}else{
    		bServerSide=options.bServerSide;
    	}
    	var bDestroy;
    	if(options.bDestroy===undefined){
    		bDestroy=false;
    	}else{
    		bDestroy = options.bDestroy;
    	}
    	var aaSorting=options.aaSorting||[[ 0, "desc" ]];
    	var oTableTools=options.oTableTools||{}
    	  	return table.dataTable({"sAjaxSource":options.sAjaxSource,
    			"aoColumns":options.aoColumns,
    			"bServerSide":bServerSide,
    			"bDestroy":bDestroy,
    			"aaSorting":  aaSorting,
    			"fnServerParams": function (aoData ) {
    				for(var i=0;i<data.length;i++){
    					aoData.push(data[i]);
    				}
    			},
    			"fnInitComplete":(options.fnInitComplete||function(){}),
    			"fnDrawCallback":(options.fnDrawCallback||function(dd){console.log(dd)})
    	});
  
    };
    
    SP.serializeToArray = function(obj) {
        var o = {};
        var a = obj.serializeArray();
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [ o[this.name] ];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
    
    SP.serializeToJson = function(obj) {
        var o = {};
        var a = obj.serializeArray();
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [ o[this.name] ];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return JSON2.stringify(o);
    };
    
    SP.serializeObjectsJson = function(a) {
        var o = {};
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [ o[this.name] ];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return JSON2.stringify(o);
    };
    
    SP.serializeItems=function(obj){
    	 var o = {};
    	 $.each(obj, function() {
    		 if(null !=this.value && this.value.length>0)
    		 o[this.alt] = this.value;
    	 });
    	 return JSON2.stringify(o);
    };
    
    SP.getTimeBetween=function(begin,end){
    	begin = begin.replace(/\-/g,'\/');
    	end = end.replace(/\-/g,'\/');
    	var b= parseInt(new Date(begin).getTime()/1000/60/60/24);
    	var e=parseInt(new Date(end).getTime()/1000/60/60/24);
    	return  parseInt(e-b);
    }
    
    SP.formatImage=function (oImage,width)
	{
		var ImageScale=1;
	 var oriwidth=oImage.width;
	 var oriheight=oImage.height;
	 if(oriwidth>width)
	 {
	    ImageScale=width/oriwidth;
	    oImage.width=width;
	    oImage.height=oriheight*(ImageScale);
	 } else{
	    oImage.width=oriwidth;
	    oImage.height=oriheight;
	    }
	} 
	
    SP.getFormatDate=function(date, dateformat) {
	  date = new Date(date);
	      if (isNaN(date)) return null;
	      var  format = dateformat || 'yyyy-MM-dd hh:mm:ss';
	      var o = {
	          "M+": date.getMonth() + 1,
	          "d+": date.getDate(),
	          "h+": date.getHours(),
	          "m+": date.getMinutes(),
	          "s+": date.getSeconds(),
	          "q+": Math.floor((date.getMonth() + 3) / 3),
	          "S": date.getMilliseconds()
	      }
	      if (/(y+)/.test(format))
	      {
	          format = format.replace(RegExp.$1, (date.getFullYear() + "")
	      .substr(4 - RegExp.$1.length));
	      }
	      for (var k in o)
	      {
	          if (new RegExp("(" + k + ")").test(format))
	          {
	              format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
	          : ("00" + o[k]).substr(("" + o[k]).length));
	          }
	      }
	      return format;
	  }
    
    SP.trim=function(str){
    	return str.replace(/(^\s*)|(\s*$)/g, "");
    };
    SP.getTimeBetween=function(begin,end){
		var b= parseInt(begin/1000/60/60/24);
		var e=parseInt(end/1000/60/60/24);
		return  parseInt(e-b);
};
SP.addCommas=function (nStr) {
    nStr += '';
    var x = nStr.split('.');
    var x1 = x[0];
    var x2 = (x.length > 1)?('.' + x[1]): '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
};
    
    SP.scrolFixedTop=function(obj){
    	obj.each(function(){
		    var o = this;
		    var timeout = null;
		    var offset_top = $(o).offset().top;
		    $(o).css({
		        "width": $(o).width(),
		        "height": $(o).height()
		    });
		    $(window).scroll(function(){
		        clearTimeout(timeout);
		        if($("body").get(0).getBoundingClientRect().top <= -(offset_top + 10)){
		            if($.browser.msie && $.browser.version < 8){
		                timeout = setTimeout(function(){
		                    $(o).css({
		                        "position": "absolute",
		                        "z-index": 1000,
		                        "left": $(o).offset().left
		                    })
		                    .animate({
		                        "top": - $("body").get(0).getBoundingClientRect().top
		                    })
		                    .addClass("col-fixed");
		                }, 200);
		            }else{
		                $(o).css({
		                    "position": "fixed",
		                    "top": 0,
		                    "z-index": 1000,
		                    "left": $(o).offset().left
		                })
		                .addClass("col-fixed");
		            }
		        }else{
		            $(o).css({
		                "position": "relative",
		                "top": 0,
		                "left": 0
		            })
		            .removeClass("col-fixed");
		        }
		    });
		});
    }

})(jQuery);