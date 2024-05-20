/* Place any Javascript here you want loaded in test.xhtml */
function sheetExtender() {
  console.log("sheetExtender called");
  this.cfg.trimWhitespace = true;
}

function updateSheet() {
  console.log("updateSheet called");
  var $hot = PF("sheetWidget").ht;
  $hot.updateSettings({
    contextMenu: {
      callback: function (key, options) {
        if (key == "remove_row") {
          setTimeout(() => {
            console.log(options);
            let startIdx = options[0]["start"]["row"];
            let endIdx = options[0]["end"]["row"];
            console.log("removing");
            handleRowRemove(startIdx, endIdx);
          }, 100);
        } else if (key === "row_above") {
          console.log(options);
          let startIdx = options[0]["start"]["row"];
          let endIdx = options[0]["end"]["row"];
          console.log("adding row");
          handleRowAdd(startIdx, "above");
        }
      },
      items: {
        row_above: {
          name: "Insert row above",
          callback: function (key, options) {
            $hot.alter("insert_row", $hot.getSelectedLast()[0]);
            // does some things after click
          },
        },
        remove_row: {
          name: "Remove row(s)",
          disabled: function () {
            // if first row, disable this option
            return $hot.getSelected()[0] === 0;
          },
        },
      },
    },
  });
}

function handleRowRemove(startIndex, endIndex) {
  var promise = removeRowRC([
    { name: "startIndex", value: startIndex },
    { name: "endIndex", value: endIndex },
  ]);
  promise
    .then(function (responseData) {
      console.log("handleRowRemove request successful: ", responseData.status);
    })
    .catch(function (error) {
      console.error("Request failed", error);
    });
}

function handleRowAdd(startIndex, position) {
  var promise = addRowRC([
    { name: "startIndex", value: startIndex },
    { name: "position", value: position },
  ]);
  promise
    .then(function (responseData) {
      console.log("handleRowAdd request successful: ", responseData.status);
    })
    .catch(function (error) {
      console.error("Request failed", error);
    });
}
