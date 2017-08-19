const path = require('path')
const {app} = require('electron')
const {BrowserWindow} = require('electron')
const cp = require('child_process');


app.on('window-all-closed', () => {
  app.quit()
})

app.on('ready', () => {
  let win = new BrowserWindow({width: 971, height: 400})
  // win.webContents.openDevTools()

  console.log('path', path.join(__dirname, 'www', 'index.html'))
  win.loadURL(path.join('file://', __dirname, 'www', 'index.html'))
})
