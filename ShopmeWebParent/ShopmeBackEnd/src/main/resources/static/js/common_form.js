$(document).ready(function () {
    $("#buttonCancel").on('click', function () {
        window.location = moduleUrl;
    });
    $("#fileImage").change(function () {
        fileSize = this.files[0].size;
        if (fileSize > 1048576) {
            this.setCustomValidity("You must choose a image less than 1MB!")
            this.reportValidity();
        } else {
            this.setCustomValidity("");
            showImageThumbnail(this);
        }
    });
});
/**
 * Hàm hiển thị hình ảnh
*/
function showImageThumbnail(fileInput) {
    var file = fileInput.files[0];
    var reader = new FileReader();
    reader.onload = function (e) {
        $("#thumbnail").attr("src", e.target.result);
    };
    reader.readAsDataURL(file);
}