<template lang="jade">
#menu
  i.icon.keyboard(v-if="!showKeyboard", @click="toggleKeyboard()")
  i.icon.exit(v-if="showKeyboard", @click="toggleKeyboard()")
</template>

<script>

export default {
  data () {
    return {
      showKeyboard: false
    }
  },

  methods: {
    toggleKeyboard () {
      this.showKeyboard = ! this.showKeyboard
      this.$props.onKeyboardChange(this.showKeyboard)
    }
  },

  props: {
      onKeyboardChange: {
          type: Function,//Object,
          default () {
            return ()=>{};
          }
      }

  },

  mounted () {

  },

  components: {
  }
}
</script>

<style lang="stylus" scoped>
#menu
  bottom: 0px;
  width: 100%;
  color: #666;
  position: absolute;
  padding-left: 50px;
  border-top: 5px solid #666;

i.icon
  cursor: pointer;
  position: relative;
  bottom: 0px;
  left: 0px;
  font-size: 52px;

#menu i.icon.keyboard:after
  content: '\e721';
  color: #666;

i.icon.exit:after
  content: '\e811';
</style>
