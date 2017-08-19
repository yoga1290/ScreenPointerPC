<template lang="jade">
#app(role='main')
  #tool-tip.col-xs-12.col-sm-8.col-sm-offset-2 {{text}}
    i.icon.tablet
</template>

<script>

export default {
  data () {
    return {
      text: ''
    }
  },

  methods: {
  },

  props: {
  },

  destroy () {
  },

  mounted () {
    if (window.require) {
      let electron = window.require('electron')
      let server = electron.remote.require('./server.js')
      server.start((url) => {
        this.text = url
      })
    }
  },

  components: {
  }
}
</script>

<style lang="stylus" scoped>
#app
  background-color: #ccc;
  height: 100%;
  width:100%;
#tool-tip
  border: 5px solid #666;
  border-radius: 5px;
  margin-top: 70px;
  color: #666;
  font-size: 50px;
  padding-top: 5px;

#tool-tip:after
  position: relative;
  width: 0;
  content: "";
  border-right: 20px solid transparent;
  border-top: 20px solid #666;
  border-left: 20px solid transparent;
  border-radius: 5px;
  top: 72px;
  float: left;

i.icon.tablet
  font-size: 72px;
  bottom: -72px;
  position: relative;
  float: left;

i.icon.tablet:after
  content: '\e80a';
  color: #666;

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
