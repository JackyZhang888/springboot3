// 设置保存目录
function setSavePath(setValue) {
    // 阻止按钮的默认行为（例如表单提交）
    event.preventDefault();
    // 获取输入框元素
    var savePathInput = document.getElementById("save-path");
    // 将传递进来的值赋给输入框的value属性
    savePathInput.value = setValue;
}

// 点击放大图片
function toggleEnlarge(event) {
    var image = event.target; // 获取被点击的图片元素

    if (image.classList.contains('enlarged')) {
        image.classList.remove('enlarged'); // 移除已经被放大的类名
    } else {
        image.classList.add('enlarged'); // 添加放大的类名
    }
}