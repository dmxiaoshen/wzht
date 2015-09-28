/**
 * 基础类库
 * 
 */
Global=window['Global']||{};
Margin = {};
M=Margin;
Global.rowNum=30;
Global.NAN=999999999999999;
// for old browsers
window["undefined"] = window["undefined"];

/**
 * @class Margin
 * Margin core utilities and functions.
 * @singleton
 */

/**
 * Copies all the properties of config to obj.
 * @param {Object} obj The receiver of the properties
 * @param {Object} config The source of the properties
 * @param {Object} defaults A different object that will also be applied for default values
 * @return {Object} returns obj
 * @member Margin apply
 */
Margin.apply = function(o, c, defaults){
    if(defaults){
        // no "this" reference for friendly out of scope calls
        Margin.apply(o, defaults);
    }
    if(o && c && typeof c == 'object'){
        for(var p in c){
            o[p] = c[p];
        }
    }
    return o;
};

(function(){
	var idSeed = 0;
    var ua = navigator.userAgent.toLowerCase();

    var isStrict = document.compatMode == "CSS1Compat",
        isOpera = ua.indexOf("opera") > -1,
        isChrome = ua.indexOf("chrome") > -1,
        isSafari = !isChrome && (/webkit|khtml/).test(ua),
        isSafari3 = isSafari && ua.indexOf('webkit/5') != -1,
        isIE = !isOpera && ua.indexOf("msie") > -1,
        isIE7 = !isOpera && ua.indexOf("msie 7") > -1,
        isIE8 = !isOpera && ua.indexOf("msie 8") > -1,
        isGecko = !isSafari && !isChrome && ua.indexOf("gecko") > -1,
        isGecko3 = isGecko && ua.indexOf("rv:1.9") > -1,
        isBorderBox = isIE && !isStrict,
        isWindows = (ua.indexOf("windows") != -1 || ua.indexOf("win32") != -1),
        isMac = (ua.indexOf("macintosh") != -1 || ua.indexOf("mac os x") != -1),
        isAir = (ua.indexOf("adobeair") != -1),
        isLinux = (ua.indexOf("linux") != -1),
        isSecure = window.location.href.toLowerCase().indexOf("https") === 0;

    // remove css image flicker
	if(isIE && !isIE7){
        try{
            document.execCommand("BackgroundImageCache", false, true);
        }catch(e){}
    }

    Margin.apply(Margin, {
        /**
         * True if the browser is in strict (standards-compliant) mode, as opposed to quirks mode
         * @type Boolean
         */
        isStrict : isStrict,
        /**
         * True if the page is running over SSL
         * @type Boolean
         */
        isSecure : isSecure,


        /**
         * Copies all the properties of config to obj if they don't already exist.
         * @param {Object} obj The receiver of the properties
         * @param {Object} config The source of the properties
         * @return {Object} returns obj
         */
        applyIf : function(o, c){
            if(o && c){
                for(var p in c){
                    if(typeof o[p] == "undefined"){ o[p] = c[p]; }
                }
            }
            return o;
        },

      
        /**
         * Generates unique ids. If the element already has an id, it is unchanged
         * @param {Mixed} el (optional) The element to generate an id for
         * @param {String} prefix (optional) Id prefix (defaults "margin-gen")
         * @return {String} The generated Id.
         */
        id : function(prefix){
            prefix = prefix || "margin-gen";
            return prefix + (++idSeed);
        },


        getBytesCount: function (str){
        	var bytesCount = 0;
        	if (str != null){
	            for (var i = 0; i < str.length; i++){
	            	var c = str.charAt(i);
	            	if ( /^[\u0000-\u00ff]$/.test(c)){
	            		bytesCount += 1;
	            	} else {
	            		bytesCount += 2;
	            	}
	          
	            }
        	}
            return bytesCount;
       },
        /**
         * Marginends one class with another class and optionally overrides members with the passed literal. This class
         * also adds the function "override()" to the class that can be used to override
         * members on an instance.
         * * <p>
         * This function also supports a 2-argument call in which the subclass's constructor is
         * not passed as an argument. In this form, the parameters are as follows:</p><p>
         * <div class="mdetail-params"><ul>
         * <li><code>superclass</code>
         * <div class="sub-desc">The class being extended</div></li>
         * <li><code>overrides</code>
         * <div class="sub-desc">A literal with members which are copied into the subclass's
         * prototype, and are therefore shared among all instances of the new class.<p>
         * This may contain a special member named <tt><b>constructor</b></tt>. This is used
         * to define the constructor of the new class, and is returned. If this property is
         * <i>not</i> specified, a constructor is generated and returned which just calls the
         * superclass's constructor passing on its parameters.</p></div></li>
         * </ul></div></p><p>
         * For example, to create a subclass of the Margin GridPanel:
         * <pre><code>
    MyGridPanel = Margin.extend(Margin.grid.GridPanel, {
        constructor: function(config) {
            // Your preprocessing here
        	MyGridPanel.superclass.constructor.apply(this, arguments);
            // Your postprocessing here
        },

        yourMethod: function() {
            // etc.
        }
    });
</code></pre>
         * </p>
         * @param {Function} subclass The class inheriting the functionality
         * @param {Function} superclass The class being extended
         * @param {Object} overrides (optional) A literal with members which are copied into the subclass's
         * prototype, and are therefore shared between all instances of the new class.
         * @return {Function} The subclass constructor.
         * @method extend
         */
        extend : function(){
            // inline overrides
            var io = function(o){
                for(var m in o){
                    this[m] = o[m];
                }
            };
            var oc = Object.prototype.constructor;

            return function(sb, sp, overrides){
                if(typeof sp == 'object'){
                    overrides = sp;
                    sp = sb;
                    sb = overrides.constructor != oc ? overrides.constructor : function(){sp.apply(this, arguments);};
                }
                var F = function(){}, sbp, spp = sp.prototype;
                F.prototype = spp;
                sbp = sb.prototype = new F();
                sbp.constructor=sb;
                sb.superclass=spp;
                if(spp.constructor == oc){
                    spp.constructor=sp;
                }
                sb.override = function(o){
                    Margin.override(sb, o);
                };
                sbp.override = io;
                Margin.override(sb, overrides);
                sb.extend = function(o){Margin.extend(sb, o);};
                return sb;
            };
        }(),

        /**
         * Adds a list of functions to the prototype of an existing class, overwriting any existing methods with the same name.
         * Usage:<pre><code>
			Margin.override(MyClass, {
			    newMethod1: function(){
			        // etc.
			    },
			    newMethod2: function(foo){
			        // etc.
			    }
			});
			 </code></pre>
         * @param {Object} origclass The class to override
         * @param {Object} overrides The list of functions to add to origClass.  This should be specified as an object literal
         * containing one or more methods.
         * @method override
         */
        override : function(origclass, overrides){
            if(overrides){
                var p = origclass.prototype;
                for(var method in overrides){
                    p[method] = overrides[method];
                }
                if(Margin.isIE && overrides.toString != origclass.toString){
                    p.toString = overrides.toString;
                }
            }
        },

        /**
         * Creates namespaces to be used for scoping variables and classes so that they are not global.  Usage:
         * <pre><code>
				Margin.namespace('Company', 'Company.data');
				Company.Widget = function() { ... }
				Company.data.CustomStore = function(config) { ... }
				</code></pre>
         * @param {String} namespace1
         * @param {String} namespace2
         * @param {String} etc
         * @method namespace
         */
        namespace : function(){
            var a=arguments, o=null, i, j, d, rt;
            for (i=0; i<a.length; ++i) {
                d=a[i].split(".");
                rt = d[0];
                eval('if (typeof ' + rt + ' == "undefined"){' + rt + ' = {};} o = ' + rt + ';');
                for (j=1; j<d.length; ++j) {
                    o[d[j]]=o[d[j]] || {};
                    o=o[d[j]];
                }
            }
        },

        /**
         * Takes an object and converts it to an encoded URL. e.g. Margin.urlEncode({foo: 1, bar: 2}); would return "foo=1&bar=2".  Optionally, property values can be arrays, instead of keys and the resulting string that's returned will contain a name/value pair for each array value.
         * @param {Object} o
         * @return {String}
         */
        urlEncode : function(o){
            if(!o){
                return "";
            }
            var buf = [];
            for(var key in o){
                var ov = o[key], k = encodeURIComponent(key);
                var type = typeof ov;
                if(type == 'undefined'){
                    buf.push(k, "=&");
                }else if(type != "function" && type != "object"){
                    buf.push(k, "=", encodeURIComponent(ov), "&");
                }else if(Margin.isDate(ov)){
                    var s = Margin.encode(ov).replace(/"/g, '');
                    buf.push(k, "=", s, "&");
                }else if(Margin.isArray(ov)){
                    if (ov.length) {
	                    for(var i = 0, len = ov.length; i < len; i++) {
	                        buf.push(k, "=", encodeURIComponent(ov[i] === undefined ? '' : ov[i]), "&");
	                    }
	                } else {
	                    buf.push(k, "=&");
	                }
                }
            }
            buf.pop();
            return buf.join("");
        },

        /**
         * Takes an encoded URL and and converts it to an object. e.g. Margin.urlDecode("foo=1&bar=2"); would return {foo: "1", bar: "2"}
         * or Margin.urlDecode("foo=1&bar=2&bar=3&bar=4", false); would return {foo: "1", bar: ["2", "3", "4"]}.
         * @param {String} string
         * @param {Boolean} overwrite (optional) Items of the same name will overwrite previous values instead of creating an an array (Defaults to false).
         * @return {Object} A literal with members
         */
        urlDecode : function(string, overwrite){
            if(!string || !string.length){
                return {};
            }
            var obj = {};
            var pairs = string.split('&');
            var pair, name, value;
            for(var i = 0, len = pairs.length; i < len; i++){
                pair = pairs[i].split('=');
                name = decodeURIComponent(pair[0]);
                value = decodeURIComponent(pair[1]);
                if(overwrite !== true){
                    if(typeof obj[name] == "undefined"){
                        obj[name] = value;
                    }else if(typeof obj[name] == "string"){
                        obj[name] = [obj[name]];
                        obj[name].push(value);
                    }else{
                        obj[name].push(value);
                    }
                }else{
                    obj[name] = value;
                }
            }
            return obj;
        },


        // deprecated
        combine : function(){
            var as = arguments, l = as.length, r = [];
            for(var i = 0; i < l; i++){
                var a = as[i];
                if(Margin.isArray(a)){
                    r = r.concat(a);
                }else if(a.length !== undefined && !a.substr){
                    r = r.concat(Array.prototype.slice.call(a, 0));
                }else{
                    r.push(a);
                }
            }
            return r;
        },

        /**
         * Escapes the passed string for use in a regular expression
         * @param {String} str
         * @return {String}
         */
        escapeRe : function(s) {
            return s.replace(/([.*+?^${}()|[\]\/\\])/g, "\\$1");
        },

        // internal
        callback : function(cb, scope, args, delay){
            if(typeof cb == "function"){
                if(delay){
                    cb.defer(delay, scope, args || []);
                }else{
                    cb.apply(scope, args || []);
                }
            }
        },

        /**
         * True if the detected browser is Opera.
         * @type Boolean
         */
        isOpera : isOpera,
        /**
         * True if the detected browser is Chrome.
         * @type Boolean
         */
        isChrome : isChrome,
        /**
         * True if the detected browser is Safari.
         * @type Boolean
         */
        isSafari : isSafari,
        /**
         * True if the detected browser is Safari 3.x.
         * @type Boolean
         */
        isSafari3 : isSafari3,
        /**
         * True if the detected browser is Safari 2.x.
         * @type Boolean
         */
        isSafari2 : isSafari && !isSafari3,
        /**
         * True if the detected browser is Internet Explorer.
         * @type Boolean
         */
        isIE : isIE,
        /**
         * True if the detected browser is Internet Explorer 6.x.
         * @type Boolean
         */
        isIE6 : isIE && !isIE7 && !isIE8,
        /**
         * True if the detected browser is Internet Explorer 7.x.
         * @type Boolean
         */
        isIE7 : isIE7,
        /**
         * True if the detected browser is Internet Explorer 8.x.
         * @type Boolean
         */
        isIE8 : isIE8,
        /**
         * True if the detected browser uses the Gecko layout engine (e.g. Mozilla, Firefox).
         * @type Boolean
         */
        isGecko : isGecko,
        /**
         * True if the detected browser uses a pre-Gecko 1.9 layout engine (e.g. Firefox 2.x).
         * @type Boolean
         */
        isGecko2 : isGecko && !isGecko3,
        /**
         * True if the detected browser uses a Gecko 1.9+ layout engine (e.g. Firefox 3.x).
         * @type Boolean
         */
        isGecko3 : isGecko3,
        /**
         * True if the detected browser is Internet Explorer running in non-strict mode.
         * @type Boolean
         */
        isBorderBox : isBorderBox,
        /**
         * True if the detected platform is Linux.
         * @type Boolean
         */
        isLinux : isLinux,
        /**
         * True if the detected platform is Windows.
         * @type Boolean
         */
        isWindows : isWindows,
        /**
         * True if the detected platform is Mac OS.
         * @type Boolean
         */
        isMac : isMac,
        /**
         * True if the detected platform is Adobe Air.
         * @type Boolean
         */
        isAir : isAir,
        formatPercent:function(source,precisefigures) {
        	 if(!source&&source!==0&&source!=="0"){
    		 	return "";
    		 }
        	 var figures=precisefigures?precisefigures:2;
        	 var fNumber=parseFloat(source);
        	 var p=(fNumber*100).toFixed(figures);
    		 return Margin.formatNumber(p,3,",",figures)+"%";
    	},
        formatNumber:function(str, step, splitor,decimalPlaces) {
        	if(str==null||str==undefined){
        		return "";
        	}
        	//保留小数位数
        	if(!decimalPlaces&&decimalPlaces!==0){
        		decimalPlaces=2;
        	}
        	if(str===0){
        		return parseFloat(str).toFixed(decimalPlaces);
        	}
        	if(str){
           		str = parseFloat(str).toFixed(decimalPlaces);
        	}
        	str = str.toString();
        	var sign="";
    		if(str.indexOf("-")==0){
    			sign="-";
    			str=str.slice(1);
    		}else if(str.indexOf("+")==0){
    			sign="+";
    			str=str.slice(1);
    		}
        
    		var strArr= new Array(); //定义一数组
    		strArr=str.split("."); //字符分割
    		var decimalPoint="";
    		if(strArr.length>0){
    			str = strArr[0];
    			decimalPoint = strArr[1];
    		}
    		var len = str.length;

    		if(len > step) {
    			 var remainder = len%step;
    			 var parseNum = parseInt(len/step);
    			 var arr = [];
    			 var first = str.substr(0, remainder);
    			 if(first != '') {
    				 arr.push(first);
    			 };
    			 for(var i=0; i<parseNum; i++) {
    				 arr.push(str.substr(remainder + i*step, step));									 
    			 };
    			 str = arr.join(splitor);
    		 };
    		 if(decimalPoint){
    			str = str+"."+decimalPoint;
    		 }
    		 return sign+str;
    	},
    	overrideNull:function(obj){
    		if(obj||obj==0){
    			return obj;
    		}
    		return "";
    	}

    });

    // in intellij using keyword "namespace" causes parsing errors
    Margin.ns = Margin.namespace;
})();



/**
 * @class Function
 * These functions are available on every Function object (any JavaScript function).
 */
Margin.apply(Function.prototype, {
     /**
     * Creates a callback that passes arguments[0], arguments[1], arguments[2], ...
     * Call directly on any function. Example: <code>myFunction.createCallback(arg1, arg2)</code>
     * Will create a function that is bound to those 2 args. <b>If a specific scope is required in the
     * callback, use {@link #createDelegate} instead.</b> The function returned by createCallback always
     * executes in the window scope.
     * <p>This method is required when you want to pass arguments to a callback function.  If no arguments
     * are needed, you can simply pass a reference to the function as a callback (e.g., callback: myFn).
     * However, if you tried to pass a function with arguments (e.g., callback: myFn(arg1, arg2)) the function
     * would simply execute immediately when the code is parsed. Example usage:
     * <pre><code>
var sayHi = function(name){
    alert('Hi, ' + name);
}

// clicking the button alerts "Hi, Fred"
new Margin.Button({
    text: 'Say Hi',
    renderTo: Margin.getBody(),
    handler: sayHi.createCallback('Fred')
});
</code></pre>
     * @return {Function} The new function
    */
    createCallback : function(/*args...*/){
        // make args available, in function below
        var args = arguments;
        var method = this;
        return function() {
            return method.apply(window, args);
        };
    },

    /**
     * Creates a delegate (callback) that sets the scope to obj.
     * Call directly on any function. Example: <code>this.myFunction.createDelegate(this, [arg1, arg2])</code>
     * Will create a function that is automatically scoped to obj so that the <tt>this</tt> variable inside the
     * callback points to obj. Example usage:
     * <pre><code>
var sayHi = function(name){
    // Note this use of "this.text" here.  This function expects to
    // execute within a scope that contains a text property.  In this
    // example, the "this" variable is pointing to the btn object that
    // was passed in createDelegate below.
    alert('Hi, ' + name + '. You clicked the "' + this.text + '" button.');
}

var btn = new Margin.Button({
    text: 'Say Hi',
    renderTo: Margin.getBody()
});

// This callback will execute in the scope of the
// button instance. Clicking the button alerts
// "Hi, Fred. You clicked the "Say Hi" button."
btn.on('click', sayHi.createDelegate(btn, ['Fred']));
</code></pre>
     * @param {Object} obj (optional) The object for which the scope is set
     * @param {Array} args (optional) Overrides arguments for the call. (Defaults to the arguments passed by the caller)
     * @param {Boolean/Number} appendArgs (optional) if True args are appended to call args instead of overriding,
     *                                             if a number the args are inserted at the specified position
     * @return {Function} The new function
     */
    createDelegate : function(obj, args, appendArgs){
        var method = this;
        return function() {
            var callArgs = args || arguments;
            if(appendArgs === true){
                callArgs = Array.prototype.slice.call(arguments, 0);
                callArgs = callArgs.concat(args);
            }else if(typeof appendArgs == "number"){
                callArgs = Array.prototype.slice.call(arguments, 0); // copy arguments first
                var applyArgs = [appendArgs, 0].concat(args); // create method call params
                Array.prototype.splice.apply(callArgs, applyArgs); // splice them in
            }
            return method.apply(obj || window, callArgs);
        };
    },

    /**
     * Calls this function after the number of millseconds specified, optionally in a specific scope. Example usage:
     * <pre><code>
var sayHi = function(name){
    alert('Hi, ' + name);
}

// executes immediately:
sayHi('Fred');

// executes after 2 seconds:
sayHi.defer(2000, this, ['Fred']);

// this syntax is sometimes useful for deferring
// execution of an anonymous function:
(function(){
    alert('Anonymous');
}).defer(100);
</code></pre>
     * @param {Number} millis The number of milliseconds for the setTimeout call (if 0 the function is executed immediately)
     * @param {Object} obj (optional) The object for which the scope is set
     * @param {Array} args (optional) Overrides arguments for the call. (Defaults to the arguments passed by the caller)
     * @param {Boolean/Number} appendArgs (optional) if True args are appended to call args instead of overriding,
     *                                             if a number the args are inserted at the specified position
     * @return {Number} The timeout id that can be used with clearTimeout
     */
    defer : function(millis, obj, args, appendArgs){
        var fn = this.createDelegate(obj, args, appendArgs);
        if(millis){
            return setTimeout(fn, millis);
        }
        fn();
        return 0;
    },

    /**
     * Create a combined function call sequence of the original function + the passed function.
     * The resulting function returns the results of the original function.
     * The passed fcn is called with the parameters of the original function. Example usage:
     * <pre><code>
var sayHi = function(name){
    alert('Hi, ' + name);
}

sayHi('Fred'); // alerts "Hi, Fred"

var sayGoodbye = sayHi.createSequence(function(name){
    alert('Bye, ' + name);
});

sayGoodbye('Fred'); // both alerts show
</code></pre>
     * @param {Function} fcn The function to sequence
     * @param {Object} scope (optional) The scope of the passed fcn (Defaults to scope of original function or window)
     * @return {Function} The new function
     */
    createSequence : function(fcn, scope){
        if(typeof fcn != "function"){
            return this;
        }
        var method = this;
        return function() {
            var retval = method.apply(this || window, arguments);
            fcn.apply(scope || this || window, arguments);
            return retval;
        };
    },

    /**
     * Creates an interceptor function. The passed fcn is called before the original one. If it returns false,
     * the original one is not called. The resulting function returns the results of the original function.
     * The passed fcn is called with the parameters of the original function. Example usage:
     * <pre><code>
var sayHi = function(name){
    alert('Hi, ' + name);
}

sayHi('Fred'); // alerts "Hi, Fred"

// create a new function that validates input without
// directly modifying the original function:
var sayHiToFriend = sayHi.createInterceptor(function(name){
    return name == 'Brian';
});

sayHiToFriend('Fred');  // no alert
sayHiToFriend('Brian'); // alerts "Hi, Brian"
</code></pre>
     * @param {Function} fcn The function to call before the original
     * @param {Object} scope (optional) The scope of the passed fcn (Defaults to scope of original function or window)
     * @return {Function} The new function
     */
    createInterceptor : function(fcn, scope){
        if(typeof fcn != "function"){
            return this;
        }
        var method = this;
        return function() {
            fcn.target = this;
            fcn.method = method;
            if(fcn.apply(scope || this || window, arguments) === false){
                return;
            }
            return method.apply(this || window, arguments);
        };
    }
});

/**
 * @class String
 * These functions are available as static methods on the JavaScript String object.
 */
Margin.applyIf(String, {

    /**
     * Escapes the passed string for ' and \
     * @param {String} string The string to escape
     * @return {String} The escaped string
     * @static
     */
    escape : function(string) {
        return string.replace(/('|\\)/g, "\\$1");
    },

    /**
     * Pads the left side of a string with a specified character.  This is especially useful
     * for normalizing number and date strings.  Example usage:
     * <pre><code>
var s = String.leftPad('123', 5, '0');
// s now contains the string: '00123'
</code></pre>
     * @param {String} string The original string
     * @param {Number} size The total length of the output string
     * @param {String} char (optional) The character with which to pad the original string (defaults to empty string " ")
     * @return {String} The padded string
     * @static
     */
    leftPad : function (val, size, ch) {
        var result = new String(val);
        if(!ch) {
            ch = " ";
        }
        while (result.length < size) {
            result = ch + result;
        }
        return result.toString();
    },

    /**
     * Allows you to define a tokenized string and pass an arbitrary number of arguments to replace the tokens.  Each
     * token must be unique, and must increment in the format {0}, {1}, etc.  Example usage:
     * <pre><code>
		var cls = 'my-class', text = 'Some text';
		var s = String.format('&lt;div class="{0}">{1}&lt;/div>', cls, text);
		// s now contains the string: '&lt;div class="my-class">Some text&lt;/div>'
		</code></pre>
     * @param {String} string The tokenized string to be formatted
     * @param {String} value1 The value to replace token {0}
     * @param {String} value2 Etc...
     * @return {String} The formatted string
     * @static
     */
    format : function(format){
        var args = Array.prototype.slice.call(arguments, 1);
        return format.replace(/\{(\d+)\}/g, function(m, i){
            return args[i];
        });
    }
});

/**
 * Utility function that allows you to easily switch a string between two alternating values.  The passed value
 * is compared to the current string, and if they are equal, the other value that was passed in is returned.  If
 * they are already different, the first value passed in is returned.  Note that this method returns the new value
 * but does not change the current string.
 * <pre><code>
// alternate sort directions
sort = sort.toggle('ASC', 'DESC');

// instead of conditional logic:
sort = (sort == 'ASC' ? 'DESC' : 'ASC');
</code></pre>
 * @param {String} value The value to compare to the current string
 * @param {String} other The new value to use if the string already equals the first value passed in
 * @return {String} The new value
 */
String.prototype.toggle = function(value, other){
    return this == value ? other : value;
};

/**
 * Trims whitespace from either end of a string, leaving spaces within the string intact.  Example:
 * <pre><code>
var s = '  foo bar  ';
alert('-' + s + '-');         //alerts "- foo bar -"
alert('-' + s.trim() + '-');  //alerts "-foo bar-"
</code></pre>
 * @return {String} The trimmed string
 */
String.prototype.trim = function(){
    var re = /^\s+|\s+$/g;
    return function(){ return this.replace(re, ""); };
}();
/**
 * @class Number
 */
Margin.applyIf(Number.prototype, {
    /**
     * Checks whether or not the current number is within a desired range.  If the number is already within the
     * range it is returned, otherwise the min or max value is returned depending on which side of the range is
     * exceeded.  Note that this method returns the constrained value but does not change the current number.
     * @param {Number} min The minimum number in the range
     * @param {Number} max The maximum number in the range
     * @return {Number} The constrained value if outside the range, otherwise the current value
     */
    constrain : function(min, max){
        return Math.min(Math.max(this, min), max);
    },
	addX : function (arg) {
		var r1, r2, m;
		var arg1=arg;
		var arg2=this;
        try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
        try { r2 = arg2.toString().split(".")[1].length ;} catch (e) { r2 = 0 ;}
        m = Math.pow(10, Math.max(r1, r2));
        return Number((arg1 * m + arg2 * m) / m);
	},
	
	subtraX:function(arg){
		 var r1, r2, m, n;
		 var arg1=this;
		 var arg2=arg;
         try { r1 = arg1.toString().split(".")[1].length ;} catch (e) { r1 = 0; }
         try { r2 = arg2.toString().split(".")[1].length ;} catch (e) { r2 = 0 ;}
         m = Math.pow(10, Math.max(r1, r2));
         //动态控制精度长度
         n = (r1 >= r2) ? r1 : r2;
         return Number(((arg1 * m - arg2 * m) / m).toFixed(n));
	},
	
	mulX:function(arg){
		var m = 0;
		var arg1=arg;
		var arg2=this;
		var s1 = arg1.toString();
		var s2 = arg2.toString();
		try { m += s1.split(".")[1].length ;} catch (e) { }
        try { m += s2.split(".")[1].length ;} catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
		
	},
	
	divX:function(arg){
		  var arg1=this;
		  var arg2=arg;
		  var t1 = 0, t2 = 0, r1, r2;
          try { t1 = arg1.toString().split(".")[1].length; } catch (e) { }
          try { t2 = arg2.toString().split(".")[1].length; } catch (e) { }
          r1 = Number(arg1.toString().replace(".", ""));
          r2 = Number(arg2.toString().replace(".", ""));
          return Number((r1 / r2) * Math.pow(10, t2 - t1));
	},
	
	powerX:function(index){
		return Number(Math.pow(this,index));
	}
});
/**
 * @class Array
 */
Margin.applyIf(Array.prototype, {
    /**
     * Checks whether or not the specified object exists in the array.
     * @param {Object} o The object to check for
     * @return {Number} The index of o in the array (or -1 if it is not found)
     */
    indexOf : function(o){
       for (var i = 0, len = this.length; i < len; i++){
 	      if(this[i] == o) return i;
       }
 	   return -1;
    },

    /**
     * Removes the specified object from the array.  If the object is not found nothing happens.
     * @param {Object} o The object to remove
     * @return {Array} this array
     */
    remove : function(o){
       var index = this.indexOf(o);
       if(index != -1){
           this.splice(index, 1);
       }
       return this;
    }
});

/**
 Returns the number of milliseconds between this date and date
 @param {Date} date (optional) Defaults to now
 @return {Number} The diff in milliseconds
 @member Date getElapsed
 */
Date.prototype.getElapsed = function(date) {
	return Math.abs((date || new Date()).getTime()-this.getTime());
};
Date.prototype.format = function(format) {
	if (!format) {
		format = "yyyy-MM-dd HH:mm:ss";
	}
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"H+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};