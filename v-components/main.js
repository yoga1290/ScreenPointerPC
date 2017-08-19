import jQuery from 'jquery'
import Vue from 'vue'
import App from './app.vue'
import Desktop from './desktop.vue'

window.$ = jQuery
// window.document.body.style.cssText = 'background-color: #606060';
window.document.body.style.cssText = 'background-color: #ccc';

let DESKTOP = (window.require && window.require('electron'))

new Vue({
  el: '#app',
  // https://vuejs.org/v2/guide/routing.html#Simple-Routing-From-Scratch
  render (createElement) {
    // https://vuejs.org/v2/guide/render-function#createElement-Arguments
    return createElement(DESKTOP ? Desktop:App, {props: {}})
  }
})
