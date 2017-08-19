const http = require('http')
const path = require('path')
const express = require('express')
const app = express()
const robot = require("robotjs");
const ip = require("./my-ip-address.js").ip;

exports.start = (callback) => {
  app.get('/**', express.static(path.join(__dirname, 'www')))

  app.post('/kb', function (req, res) {
    var text = req.query.text
    // http://robotjs.io/docs/syntax#keytapkey-modifier
    robot.typeString(text)
    res.sendStatus(200)
  })

  app.post('/click', function (req, res) {
    robot.mouseClick('left')
    res.sendStatus(200)
  })

  app.post('/mm', function (req, res) {
    var mouse = robot.getMousePos()
    var dx = parseInt(req.query.dx)
    var dy = parseInt(req.query.dy)
    //http://robotjs.io/docs/syntax#movemousex-y
    robot.moveMouse(mouse.x + dx, mouse.y + dy);
    res.sendStatus(200)
  })

  let port = -1;
  // HTTP + Express
  // http://expressjs.com/en/4x/api.html#app.listen
  let server = http.createServer(app)

  server.listen(() => {
    // https://nodejs.org/api/http.html#http_server_listen_port_hostname_backlog_callback
    port = server.address().port
    console.log('Server running ', `http://${ip}:${port}`)
    callback(`http://${ip}:${port}`)
  })
}
