//判断字符串是否为空
export function isEmpty(value) {
  if (value == null || trim(value) == "" || value == undefined) {
    return true;
  }
  return false;
}

//判断一个字符串是否为非空串
export function isNotEmpty(value) {

  return !isEmpty(value);
}

//判断字符串是否为空
export function nullToStr(value) {
  if (isEmpty(value)) {
    return "-";
  }
  return value;
}

//空格截取
export function trim(value) {
  if (value == null) {
    return "";
  }
  return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
}

//比较两个字符串（大小写敏感）
export function equals(str, that) {

  return str == that;
}

//比较两个字符串（大小写不敏感）
export function equalsIgnoreCase(str, that) {

  return String(str).toUpperCase() === String(that).toUpperCase();
}

/**
 * 数据脱敏
 * @param str 脱敏字符串
 * @param begin 起始保留长度，从0开始
 * @param end 结束保留长度，到str.length结束
 * @returns {string|*}
 */
export function desensitization(str,begin,end) {
  if (!str && (begin + end) >= str.length) {
    return "";
  }

  let leftStr = str.substring(0, begin);
  let rightStr = str.substring(end);
  let midStr = str.substring(begin,end);
  let strCon = "";
  for (let i = 0; i < midStr.length; i++) {
    strCon += "*";
  }
  return leftStr + strCon + rightStr;
}

/**
 * 姓名脱敏
 * @param name
 * @returns {string}
 */
export function desensitizationName(name) {
  if (!name) {
    return "";
  }
  let first = name.substring(0, 1);
  return first + "**";
}
