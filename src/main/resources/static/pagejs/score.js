
let layerIndex;

let $;

var toast;
/**
 * 弹出学生新增/更新对话框
 */
function showScoreDlg(id) {
    let title = "新增学生";
    if (id) {
        title = "编辑学生";
        $("#formId").css("display", "block");

        // 发送 AJAX 请求获取学生详情
        $.ajax({
            url: "/api/score/"+id,
            method: "GET"
        }).done(result=>{

            console.log(result)

            if(result.code>=0){
                let data = result.data

                // 遍历学生数据，填充到表单字段
                $.each(data, function (key, value) {
                    // 确保在 #studForm 范围内查找字段，避免全局匹配
                    const $field = $("#scoreForm [name='" + key + "']");

                    if ($field.length) { // 确保字段存在
                        if ($field.is(":radio")) {
                            // 处理单选按钮（value 匹配时选中）
                            $field.filter(`[value="${value}"]`).prop("checked", true);
                        } else if ($field.is(":checkbox")) {
                            // 处理复选框（value 为布尔值或 "yes"/"no" 时选中）
                            $field.prop("checked", value === true || value === "yes");
                        } else {
                            // 处理文本框、下拉框等
                            $field.val(value);
                        }
                    }
                });

            }else{
                //出错了
                toast.error({
                    message: result.msg,
                });
            }


        })


    } else {
        // 新增学生时重置表单
        $("#scoreForm")[0].reset();
        $("#formId").css("display", "none"); // 隐藏ID字段（如果有）
    }

    // 打开弹出层，并记录 layerIndex
    layerIndex = layer.open({
        type: 1,
        title: title,
        area: ["520px", "auto"],
        content: $("#popDlg")[0].innerHTML, // 确保表单元素正确选择
        success: function () {
            // 弹出层打开后，可选操作（如重置表单状态）
            layui.form.render(); // 重新渲染 layui 表单组件（如果使用了下拉框等）
        }
    });
}


function getSearchCondtion() {
    let formData= {}
    // 遍历每个输入元素，将其值存储到 formData 对象中
    $('#queryForm').find('input, select').each(function() {
        let name = $(this).attr( 'name'); // 获取元素的 name 属性
        let value = $(this).val(); // 获取元素的值

        // 只有 name 属性存在且值不为空才会添加到 formData 中
        if (name && value) {
            formData[name] = value;
        }
    });

    return formData
}



layui.use(function () {
    $ = layui.$
    toast = layui.toast


    //(1)验证表单是否合法
    layui.form.on("submit(stud-dlg)", function (data) {
        event.preventDefault(); // 阻止表单默认提交

        // commitStuDlg();

        // 防止重复提交地标记
        if (this.isSubmitting) return false;
        this.isSubmitting = true;

        commitScoreDlg()
            .catch(error => {
                console.error('提交失败:', error);
            })
            .finally(() => {
                this.isSubmitting = false;
            });

        return false;

    })

    //(2)表格初始化

    const table = layui.table;

    let score = getSearchCondtion();

    // 创建渲染实例
    table.render({
        elem: '#tbScore',
        url: '/api/score/getbypage', // 此处为静态模拟数据，实际使用时需换成真实接口

        method: "POST",
        contentType: 'application/json', // 确保以 JSON 格式发送
        where: {"data": score},

        page: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', fixed: 'left', width: 80, title: 'id', sort: true},
            {field: 'name', title: '姓名'},
            {
                field: 'sno',
                title: '学号',

                width: 150,

            },
            {field: 'Chinese', width: 100, title: '语文成绩', sort: true},
            {field: 'Math', width: 100, title: '数学成绩', sort: true},
            {field: 'English', width: 100, title: '英语成绩', sort: true},

            {field: 'right', title: '操作', width: 134, minWidth: 125, templet: '#editTemplate'}
        ]],
        done: function (rs) {
            //console.log(rs)
        }

    });


    // 触发单元格工具事件
    table.on('tool(tbScore)', function (obj){

        var data = obj.data; // 获得当前行数据
        // console.log(obj)
        if(obj.event === 'edit'){
            // 调用已有的 showStudenDlg 函数，传入学生ID
            showScoreDlg(data.id);
        }
    });

});

function search(){
    let score = getSearchCondtion();

    const table = layui.table;
    table.reloadData("tbScore", {
        where: {data:score}
    });
    console.log("where condition:"+JSON.stringify(score))
}

function deleteConfirm() {
    const table = layui.table;
    // 获取表格的选中状态
    const checkStatus = table.checkStatus('tbScore'); // 'tbStudent' 是你的表格的 ID 或 lay-filter
    const data = checkStatus.data;

    if (data.length === 0) {
        layer.msg('请选择要删除的学生', {icon: 2});
        return;
    }

    layer.confirm('你真的要删除这些学生吗？一旦删除，不可恢复！', {icon: 3}, function () {
        const deletePromises = data.map(item => {
            return new Promise((resolve, reject) => {
                $.ajax({
                    url: "/api/score/delete/" + item.id,
                    method: "DELETE"
                }).done(result => {
                    resolve(result);
                }).fail((jqXHR, textStatus, errorThrown) => {
                    reject({id: item.id, error: textStatus + " - " + errorThrown});
                });
            });
        });

        Promise.all(deletePromises)
            .then(() => {
                const table = layui.table;
                let score = getSearchCondtion();
                table.reload("tbScore", {
                    where: { data: score }
                });
                layer.closeAll(); // 关闭所有层(包括确认框)
                layer.msg('删除成功', {icon: 1});
            })
            .catch(errors => {
                errors.forEach(error => {
                    console.error(`删除 ID 为 ${error.id} 的学生时出错: ${error.error}`);
                });
                layer.msg('部分删除失败，请查看控制台日志', {icon: 2});
            });
    }, function () {
        // 用户取消删除操作
    });
}

// function commitStuDlg() {
//     let id = $("#id").val()
//     let formData = $(".layui-layer-page #studForm").serialize();
//     if (id != null && id != "") {
//         //是更新学生
//
//         $.ajax({
//             url: "/api/student/update",
//             method: "PUT",
//             data: formData
//         }).done(result => {
//             console.log(result);
//             if (result.code>=0) {
//                 let data = result.data
//
//                 if(data.id){
//                     //(4)读取并刷新原来的读学生列表
//                     search();
//
//                     //(3)关闭弹出层
//                     console.log("add success!")
//                     if (layerIndex)
//                         layer.close(layerIndex)
//
//                 }
//             }else{
//                 toast.error({
//                     message: result.msg,
//                 });
//             }
//
//         }).fail((jqXHR, textStatus, errorThrown) => {
//             console.error("Request failed: " + textStatus + " - " + errorThrown);
//             // 可以在这里处理错误逻辑
//             alert("An error occurred. Please try again.");
//         }).always(() => {
//             // 无论请求成功还是失败，都恢复按钮状态
//             $("#btnOK").prop("disabled", false).removeClass("layui-btn-disabled");
//         });
//
//
//     } else {
//
//         //新增学生需要进行
//         //(2)将表单数据发送到服务器的insert中,把提交按钮变灰
//
//         $.ajax({
//             url: "/api/student/add",
//             method: "POST",
//             data: formData
//         }).done(result => {
//             console.log(result);
//             if (result.code>=0){
//                 let data = result.data
//                 if(data.id){
//                     //(4)读取并刷新原来的读学生列表
//                     search();
//
//                     //(3)关闭弹出层
//                     console.log("add success!")
//                     if (layerIndex)
//                         layer.close(layerIndex)
//
//                 }else{
//                     toast.error({
//                         message: "出错了，新增没有id",
//                     });
//                 }
//             }else{
//                 toast.error({
//                     message: result.msg,
//                 });
//             }
//
//         }).fail((jqXHR, textStatus, errorThrown) => {
//             console.error("Request failed: " + textStatus + " - " + errorThrown);
//             // 可以在这里处理错误逻辑
//             alert("An error occurred. Please try again.");
//         }).always(() => {
//             // 无论请求成功还是失败，都恢复按钮状态
//             $("#btnOK").prop("disabled", false).removeClass("layui-btn-disabled");
//         });
//
//     }
//
//
//     $("#btnOK").prop("disabled", true).addClass("layui-btn-disabled"); // 禁用按钮
//
//
// }

function commitScoreDlg() {
    // 返回Promise以便更好地控制流程
    return new Promise((resolve, reject)=> {
        // 先禁用按钮，防止重复提交
        $("#btnOK").prop("disabled", true).addClass("layui-btn-disabled");

        const id = $("#id").val();
        const formData = $(".layui-layer-page #scoreForm").serialize();
        const url = id ? "/api/score/update" : "/api/score/add";
        const method = id ? "PUT" : "POST";

        $.ajax({
            url: url,
            method: method,
            data: formData
        }).done(result => {
            if (result.code >= 0 && result.data?.id) {
                // 成功情况
                search(); // 刷新列表
                if (layerIndex) {
                    layer.close(layerIndex);
                }
                resolve(result);
            }else {
                // 处理业务逻辑错误
                const errorMsg = result.msg || (id ? "更新失败" : "新增失败，没有返回id");
                toast.error({ message: errorMsg });
                reject(new Error(errorMsg));
            }
        }).fail((jqXHR, textStatus, errorThrown) =>{
            const errorMsg = '请求失败: ${textStatus} - ${errorThrown}';
            console.error(errorMsg);
            alert("系统错误，请稍后重试");
            reject(new Error(errorMsg));
        }).always(() => {
            // 无论成功失败，最后都要恢复按钮状态
            $("#btnOK").prop("disabled", false).removeClass("layui-btn-disabled");
        });
    });
}

function deleteById(id) {

    //删除
    layer.confirm('你真的要删除吗？一旦删除，不可恢复！', {icon: 3}, function () {


        $.ajax({
            url: "/api/score/delete/" + id,
            method: "DELETE"
        }).done(result => {
            search();

        })
        layer.closeAll(); // 关闭所有层(包括确认框)



    }, function () {

    });
}