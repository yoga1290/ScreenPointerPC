<template lang="jade">
#app(role='main')
  i.icon.mouse
  #touchpad
  div(v-if="showKeyboard")
    textarea#keyboard.animated.bounceInUp.col-xs-12.col-sm-10.col-sm-offset-1(@keyup="keyup", v-model="text")
    i.icon.keyboard.animated.bounceInUp
  MMenu(:onKeyboardChange="onKeyboardChange")
</template>

<script>
import MMenu from './mmenu.vue'

let getQueryString = (mp) => {
  var result = ''
  for (let k in mp) {
    result += k + '='+ mp[k] + '&'
  }
  return result
}

let q = []

let Q = (req) => {
  q.push(req.url + '?' + getQueryString(req.query))
}

let busy = false

var deQ = () => {
//  console.log('deQ', window.busy, q)

  if (busy) return;

  if (q.length > 0) {

    var url = q.splice(0, 1)[0]
    var method = 'POST'
    busy = true
    $.ajax({ method, url}).then(() => {
      busy = false
      deQ()
    })

  }
}

let interval = -1


export default {
  data () {
    return {
      text: '',
      showKeyboard: false
    }
  },

  methods: {
    keyup (e) {
      Q({
        url: 'kb',
        query: {
          text: this.text
        }
      })
      this.text = ''
    },

    onKeyboardChange (showKeyboard) {
      this.showKeyboard = showKeyboard
      if (showKeyboard) {
        window.setTimeout(()=>{
          window.$('#keyboard').focus()
        }, 50)
      }
    }
  },

  props: {
  },

  destroy () {
    if (interval > -1) {
      window.clearInterval(interval)
    }
  },

  mounted () {
      let x = 0, y = 0;
      let mup = (e)=>{
        let cx = e.pageX || e.offsetX,
            cy = e.pageY || e.offsetY
        let dx = cx - x
        let dy = cy - y
        x = cx
        y = cy

        if (dx===0 && dy===0) {
          Q({
            url: 'click',
            query: {x, y}
          })
        } else {
          Q({
            url: 'mm',
            query: {dx, dy}
          })
        }
      }
      let mmove = (e)=>{
        let cx = e.pageX || e.offsetX,
            cy = e.pageY || e.offsetY
        let dx = cx - x
        let dy = cy - y
        Q({
          url: 'mm',
          query: {dx, dy}
        })
        x = cx
        y = cy
      }
      let mdown = (e)=>{
        x = e.pageX || e.offsetX
        y = e.pageY || e.offsetY
      }
      window.document.getElementById('touchpad').addEventListener('mousedown', mdown)
      window.document.getElementById('touchpad').addEventListener('mousemove', mmove)
      window.document.getElementById('touchpad').addEventListener('mouseup', mup)

      window.document.getElementById('touchpad').addEventListener('touchstart', mdown)
      window.document.getElementById('touchpad').addEventListener('touchmove', mmove)
      window.document.getElementById('touchpad').addEventListener('touchend', mup)


      interval = window.setInterval(()=>{
          deQ()
      }, 500)
  },

  components: {
    MMenu
  }
}
</script>

<style lang="stylus">
#app
  background-color: #ccc; /*#606060;*/
  height: 100%;
  width:100%;

i.icon.mouse
  font-size: 72px;
  position: absolute;
  bottom: 50%;
  left: 50%;
i.icon.mouse:after
  content: '\e806';
  color: #808080;

i.icon.keyboard
  font-size: 72px;
  position: absolute;
  bottom: 50%;
  right: 50%;
i.icon.keyboard:after
  content: '\e721';
  color: #808080;

#touchpad
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;
#keyboard
  position: absolute;
  background-color: #5c5c5c;
  font-size: 32px;
  color: #ccc;
  border-radius: 10px;
  height: 80%;


/* https://vaadin.com/icons/download#download */
@font-face
  font-family: 'Vaadin-Icons';
  src: url('./Vaadin-Icons.eot');
  src: url('./Vaadin-Icons.eot?#iefix') format('embedded-opentype'),
       url('./Vaadin-Icons.woff') format('woff'),
       url('./Vaadin-Icons.ttf') format('truetype'),
       url('./Vaadin-Icons.svg#icomoon') format('svg');
.icon
  font-family: Vaadin-Icons;
  font-size: 16px;
  speak: none;
  font-weight: normal;
  font-variant: normal;
  font-style: normal;
  text-transform: none;
  line-height: 1;
  -webkit-font-smoothing: antialiased;
</style>
