function setSavePath(setValue) {
    // 阻止按钮的默认行为（例如表单提交）
    event.preventDefault();
    // 获取输入框元素
    var savePathInput = document.getElementById("save-path");
    // 将传递进来的值赋给输入框的value属性
    savePathInput.value = setValue;
}
