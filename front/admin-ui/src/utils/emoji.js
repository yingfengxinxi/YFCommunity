const findEle = (parent, type) => {
  return parent.tagName.toLowerCase() === type ? parent : parent.querySelector(type)
}
const trigger = (el, type) => {  // 给元素绑定事件
  const e = document.createEvent('HTMLEvents')
  e.initEvent(type, true, true)
  el.dispatchEvent(e)
}

const emoji = {
  // el：指令所绑定的元素，可以用来直接操作 DOM。
  // vnode：Vue 编译生成的虚拟节点
  bind: function (el, binding, vnode) { // 指令第一次绑定到元素时调用
    // 判断是否是emoji图标
    const isEmoji = char => {
      // 表情都是2个字符
      return char.length > 1;
    }

    const emoji2empty = str => {  // emoji图标都替换成空字符串‘’
      return Array.from(str)
        .filter(c => !isEmoji(c)).join('')
    }
    let $inp = findEle(el, 'input') || findEle(el, 'textarea')  // 判断绑定元素是否是input输入框或者富文本输入框
    el.$inp = $inp
    $inp.handle = function () {
      let val = $inp.value
      $inp.value = emoji2empty(val)  // 监听输入框的emoji图标转换成空
      trigger($inp, 'input')
    }
    $inp.addEventListener('keyup', $inp.handle)  // el添加键盘监听事件keyup
    $inp.addEventListener('blur', $inp.handle) // el添加键盘失焦事件blur
  },
  unbind: function (el) {  // 只调用一次，指令与元素解绑时调用。
    el.$inp.removeEventListener('keyup', el.$inp.handle)
    el.$inp.removeEventListener('blur', el.$inp.handle)
  },
}
export default emoji
