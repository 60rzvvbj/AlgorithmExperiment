// 设置文本不可选中
var tool = new Tool(document, window);
tool.textProhibition();

let model = 'run';

// 图的数据
let data;

if (model == 'debug') {
	data = {
		"type": 0,
		"nodes": ["A", "B", "C", "D", "E"],
		"edges": [
			["A", "B", 1],
			["A", "C", 2],
			["A", "E", 3],
			["B", "D", 4],
			["C", "D", 5],
			["C", "E", 6],
			["D", "E", 7]
		],
		"routes": [
			["2号线", "B", "A", "E", "D", "C"],
			["3号线", "A", "C", "D"],
			["5号线", "D", "E", "C", "A"]
		]
	};
}


// 图的界面
let view = getDom('.view');

// 上传功能
let main = getDom('.main'); // 获取主盒子
let input = main.getDom('input'); // 选择文件的按钮
let select = main.getDom('.selectBox'); // 选择文件的显示框
let submit = main.getDom('.submit'); // 提交按钮

if (model == 'debug') {
	main.hide();
	view.show();
}

// 提交结果提示框相关
let submitTips = getDom('.submitTips'); // 提交结果提示框盒子
let submitTipsPhoto = submitTips.getDom('.photo'); // 图片盒子
let submitTipsText = submitTips.getDom('.tipsText'); // 提示文本那盒子
let submitTipsYes = submitTips.getDom('.yes'); // 确定按钮
submitTips.state = false;

// 关闭提示框函数
function closeTips() {
	submitTips.hide();
	submitTips.state = false;
	if (data != null) {
		main.hide();
		setTimeout(function () {
			start();
		}, 50);
	}
}

// 确定按钮点击事件
submitTipsYes.addEventListener('click', closeTips);

// 回车确定
document.addEventListener('keydown', function (e) {
	if (submitTips.state && e.key == 'Enter') {
		closeTips();
	}
});

// 文件相关
let nowFile; // 当前文件
let initialFileList = input.files; // 初始文件列表
let nowFileList = initialFileList; // 当前文件列表

// 重置选择文件的框
function selectReset() {
	select.removeClass('select');
	select.innerText = '选择文件';
	let span = document.createElement('span');
	span.innerText = '＋';
	select.appendChild(span);
	select.title = '';
	// nowFile = null;
	input.files = initialFileList;
}

function judge(file) {
	let str = file.name;
	let temp = str.toLowerCase().split('.').splice(-1);
	return temp == 'ycx';
}

// 文件改变函数
function inputChangeFunction() {
	let file = input.files[0];
	if (!file) {
		return;
	}

	if (!judge(file)) {

		// 如果文件不满足格式，则将文件列表撤销
		input.files = nowFileList;
		return;
	}

	// 维护文件相关变量
	nowFile = file;
	nowFileList = input.files;

	// 显示到页面上
	select.innerText = file.name;
	select.title = file.name;
	select.addClass('select');
}

// 添加选择文件事件
input.addEventListener('change', inputChangeFunction);

// 提交成功函数
function submitTrue() {
	submitTipsPhoto.style.backgroundImage = 'url(img/submitTrue.png)';
	submitTipsText.innerText = '解析成功';
	submitTips.show();
	submitTips.state = true;
	submitTipsYes.show();
}

// 加载中函数
function submitLoading() {
	submitTipsPhoto.style.backgroundImage = 'url(img/loading.gif)';
	submitTipsText.innerText = '解析中';
	submitTips.show();
	submitTips.state = true;
	submitTipsYes.hide();
}

// 提交失败函数
function submitFalse(str) {
	submitTipsPhoto.style.backgroundImage = 'url(img/submitFalse.png)';
	submitTipsText.innerText = str ? str : '解析失败';
	submitTips.show();
	submitTips.state = true;
	submitTipsYes.show();
}

// 提交函数
function upload() {
	if (nowFile) {
		let reader = new FileReader();
		reader.readAsText(nowFile);
		reader.onload = function () {
			closeTips();
			submitTrue();
			submit.state = true;
			selectReset();
			data = JSON.parse(reader.result);
		};
		submitLoading();
	} else {
		submitFalse('请先选择文件');
		submit.state = true;
	}
}

// 提交按钮初始化节流阀
submit.state = true;

// 添加提交事件
submit.addEventListener('click', function () {
	if (submit.state) {
		submit.state = false;
		upload();
	}
});

// 主盒子相关鼠标事件
submit.addEventListener('mouseover', function () {
	main.style.transform = 'translate(-45%, -50%)';
	input.style.pointerEvents = 'none';
});
main.addEventListener('mouseout', function () {
	main.style.transform = 'translate(-50%, -50%)';
	select.style.width = '230px';
	select.style.paddingRight = '0px';
	submit.style.pointerEvents = 'auto';
	input.style.pointerEvents = 'auto';
});
input.addEventListener('mouseover', function () {
	main.style.transform = 'translate(-55%, -50%)';
	select.style.width = '360px';
	select.style.paddingRight = '50px';
	submit.style.pointerEvents = 'none';
});

// 图相关
function start() {

	// 显示图界面
	view.show();

	// 节点列表显示
	let nowNodeMessage = view.getDom('.nowNodeMessage');
	nowNodeMessage.show = function () {
		this.style.right = "0px";
	};
	nowNodeMessage.hide = function () {
		this.style.right = "-160px";
	};
	let nowNodeBox = nowNodeMessage.getDom('.nowNode');
	let adjacentNodes = nowNodeMessage.getDom('ul');

	// 通路列表显示
	let routeBox = view.getDom('.routeBox');
	let routeList = routeBox.getDom('ul');
	let routeMessage = view.getDom('.route');
	routeMessage.show = function () {
		this.style.bottom = '0px';
	};
	routeMessage.hide = function () {
		this.style.bottom = '-120px';
	};
	let routeMessageContent = routeMessage.getDom('ul');

	// 配置参数
	let lockingNode = false; // 固定
	let userPerformance = 5; // 性能参数

	let nowNode; // 当前正在拖动的节点
	let bfb = 0.8; // 节点之间线的松紧，紧0 - 1松
	let maxLength = 300; // 相邻节点间最大距离
	let minLength = 100; // 相邻节点进最小距离
	let nodeSet = new Set(); // 节点集合
	let constraintArr = new Array(); // 记录约束的数组
	let setLineArr = new Array(); // 记录要添加线条的数组
	let mx, my; // 鼠标上次的位置
	let topBoundary; // 边界约束中的边界
	let leftBoundary;
	let bottomBoundary;
	let rightBoundary;
	let boundaryMinLength; //边界约束中和边界的最小距离

	// 加载边界
	function loadBoundary() {
		topBoundary = 0; // 边界约束中的边界
		leftBoundary = 0;
		bottomBoundary = view.offsetHeight;
		rightBoundary = view.offsetWidth;
		boundaryMinLength = bottomBoundary * 0.1; //边界约束中和边界的最小距离
	}

	loadBoundary();
	window.addEventListener('resize', loadBoundary);

	let viewMultiple = 100; // 视图缩放倍数

	let nodeMap = new Map();

	// 初始化点
	for (let ch of data.nodes) {
		node = document.createElement('div');
		node.addClass('node');
		node.innerText = ch;
		view.appendChild(node);
		nodeMap.set(ch, node);
		nodeSet.add(node);
		node.edges = new Array();
		node.x = getIntRandom(leftBoundary + boundaryMinLength, rightBoundary - boundaryMinLength);
		node.y = getIntRandom(topBoundary + boundaryMinLength, bottomBoundary - boundaryMinLength);
		node.style.zIndex = 10;
		node.style.backgroundColor = randomColor(160, 220);
		node.style.color = '#333';

		// 给节点添加鼠标点击事件
		node.addEventListener('mousedown', function (e) {
			e.stopPropagation();

			// 如果有当前选中节点，就清除当前节点的相关样式
			nowNode = null;
			changeNowNode();

			// 获取鼠标位置
			mx = e.clientX;
			my = e.clientY;

			// 维护当前选中节点
			nowNode = this;
			changeNowNode();

			// 更新当前节点所有相关节点的样式
			// var t = nowNode;
			// while (t.father) {
			//     addHeightLight(t.father);
			//     t = t.father;
			// }
			// changeChild(root, addHeightLight);

			// 设置当前节点的样式
			// nowNode.style.boxShadow = '0px 0px ' + nowNode.offsetHeight + 'px ' + nowNodeBoxShadowColor;

			document.addEventListener('mousemove', move);

		});
	}

	// 初始化边
	for (let edge of data.edges) {
		let line = {};
		line.l = nodeMap.get(edge[0]);
		line.r = nodeMap.get(edge[1]);
		line.l.edges.push(line);
		line.r.edges.push(line);
		line.size = 2; // 线的粗细
		line.range = edge[2];
		line.lineStyle = 'edge';
		setLineArr.push(line);
	}

	// 初始化约束
	for (let i = 0; i < data.nodes.length; i++) {
		let node1 = nodeMap.get(data.nodes[i]);
		addConstraint(node1, null, 3, null);
		for (let j = i + 1; j < data.nodes.length; j++) {
			let node2 = nodeMap.get(data.nodes[j]);
			addConstraint(node1, node2, 2, minLength);
		}
	}
	for (let edge of setLineArr) {
		let node1 = edge.l;
		let node2 = edge.r;
		addConstraint(node1, node2, 1, maxLength);
	}

	// 通过两点找边
	function findEdgeByNodeName(name1, name2) {
		for (let edge of setLineArr) {
			let n1 = edge.l.innerText;
			let n2 = edge.r.innerText;
			if (n1 == name1 && n2 == name2 || n1 == name2 && n2 == name1) {
				return edge;
			}
		}
		return null;
	}

	// 初始化通路
	if (data.routes.length == 0) {
		routeBox.hide();
	} else {
		for (let route of data.routes) {
			let li = document.createElement('li');
			li.innerText = route[0] + ': ' + route[1] + '→' + route[route.length - 1];
			li.route = route;
			li.addEventListener('click', function () {
				nowNode = null;
				changeNowNode();
				let r = this.route;
				routeMessage.getDom('p').innerText = r[0];
				let now = r[1];
				for (let i = 2; i < r.length; i++) {
					let edge = findEdgeByNodeName(now, r[i]);
					edge.l.addClass('nodeHeightLight');
					edge.r.addClass('nodeHeightLight');
					if (edge != null) {
						edge.lineStyle = "edgeHeightLight";
					}
					now = r[i];
				}
				routeMessage.show();
				while (routeMessageContent.children.length != 0) {
					routeMessageContent.removeChild(routeMessageContent.children[0]);
				}
				for (let i = 1; i < r.length; i++) {
					let n = r[i];
					let node = nodeMap.get(n);
					let li = document.createElement('li');
					li.addClass('node');
					li.innerText = node.innerText;
					li.style.backgroundColor = node.getCSS('background-color');
					routeMessageContent.appendChild(li);
				}
			});
			routeList.appendChild(li);
		}
	}

	// 鼠标拖动的函数
	function move(e) {
		let cx = e.clientX;
		let cy = e.clientY;
		if (cx >= leftBoundary + boundaryMinLength && cx <= rightBoundary - boundaryMinLength) {
			nowNode.x = nowNode.x + (cx - mx) / (viewMultiple / 100);
			mx = cx;
		}
		if (cy >= topBoundary + boundaryMinLength && cy <= bottomBoundary - boundaryMinLength) {
			nowNode.y = nowNode.y + (cy - my) / (viewMultiple / 100);
			my = cy;
		}
	}

	nowNodeMessage.addEventListener('mousedown', function (e) {
		e.stopPropagation();
	});

	routeBox.addEventListener('mousedown', function (e) {
		e.stopPropagation();
	});

	routeMessage.addEventListener('mousedown', function (e) {
		e.stopPropagation();
	});

	document.addEventListener('mousedown', function () {
		// 如果有当前选中节点，就清除当前节点的相关样式
		if (nowNode) {
			nowNode.removeClass('nodeHeightLight');
			for (let line of nowNode.edges) {
				line.lineStyle = 'edge';
			}
		}
		nowNode = null;
		changeNowNode();
		routeMessage.hide();
	});

	// 鼠标抬起事件
	document.addEventListener('mouseup', function () {
		document.removeEventListener('mousemove', move);
	});

	// 改变当前节点的函数
	function changeNowNode() {
		if (nowNode) {
			nowNodeMessage.show();
			nowNode.addClass('nodeHeightLight');
			for (let line of nowNode.edges) {
				line.lineStyle = 'edgeHeightLight';
			}

			try {
				nowNodeBox.removeChild(nowNodeBox.children[0]);
			} catch (e) { }

			// 显示当前节点
			let node = document.createElement('div');
			node.addClass('node');
			node.innerText = nowNode.innerText;
			node.style.backgroundColor = nowNode.getCSS('background-color');
			nowNodeBox.appendChild(node);

			// 显示相邻节点
			while (adjacentNodes.children.length != 0) {
				removeDom(adjacentNodes.children[0]);
			}

			for (let edge of nowNode.edges) {
				let adjacent;
				if (edge.l == nowNode) {
					adjacent = edge.r;
				} else {
					adjacent = edge.l;
				}
				let adjacentNode = document.createElement('li');
				adjacentNode.addClass('node');
				adjacentNode.innerText = adjacent.innerText;
				adjacentNode.style.backgroundColor = adjacent.getCSS('background-color');
				adjacentNode.node = adjacent;
				adjacentNode.addEventListener('click', function () {
					if (nowNode) {
						nowNode.removeClass('nodeHeightLight');
						for (let line of nowNode.edges) {
							line.lineStyle = 'edge';
						}
					}
					nowNode = this.node;
					changeNowNode();
				});
				adjacentNodes.appendChild(adjacentNode);
			}
		} else {
			nowNodeMessage.hide();
			for (let edge of setLineArr) {
				edge.lineStyle = 'edge';
			}
			for (let node of nodeSet) {
				node.removeClass('nodeHeightLight');
			}
			// try {
			//     nowNodeBox.removeChild(nowNodeBox.children[0]);
			// } catch (e) {
			// }
			// while (adjacentNodes.children.length != 0) {
			//     removeDom(adjacentNodes.children[0]);
			// }
		}
	}

	// 生成节点间线条的函数
	function setline(edge) {
		let node1 = edge.l;
		let node2 = edge.r;
		try {
			view.removeChild(edge.line);
		} catch (e) { }

		// 创建div元素
		edge.line = document.createElement('div');

		// 计算相关坐标距离
		let x1 = node1.offsetLeft + node1.offsetWidth / 2;
		let y1 = node1.offsetTop + node1.offsetHeight / 2;
		let x2 = node2.offsetLeft + node2.offsetWidth / 2;
		let y2 = node2.offsetTop + node2.offsetHeight / 2;
		let lineLen = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		let xz = (x1 + x2) / 2;
		let yz = (y1 + y2) / 2;
		let k = (y2 - y1) / (x2 - x1);
		let jd = Math.atan(k) * 180 / Math.PI;

		// 设置线条样式属性
		edge.line.addClass(edge.lineStyle);
		edge.line.style.width = lineLen + 'px';
		edge.line.style.height = edge.size + 'px';
		edge.line.style.position = 'absolute';
		edge.line.style.left = xz - lineLen / 2 + 'px';
		edge.line.style.top = yz - edge.size / 2 + 'px';
		edge.line.style.transform = 'rotate(' + jd + 'deg)';
		// node1.line.style.boxShadow = '0px 0px 8px ' + node1.lineColor;

		// 线条权值
		let range = document.createElement('div');
		range.innerText = edge.range;
		range.style.position = 'absolute';
		range.style.left = '50%';
		range.style.top = '50%';
		range.style.fontSize = '20px';
		range.style.color = 'white';
		range.style.transform = 'translate(-50%, -50%) rotate(' + (-jd) + 'deg)';
		edge.line.appendChild(range);

		// 将线条添加到树盒子中
		view.appendChild(edge.line);
	}

	// 设置节点位置
	function setPosition(node) {
		node.style.left = node.x - node.offsetWidth / 2 + 'px';
		node.style.top = node.y - node.offsetHeight / 2 + 'px';
	}

	// 将元组添加到SetLine(设置线条)中
	function addSetLine(node1, node2) {
		setLineArr.push([node1, node2]);
	}

	// 将元组添加到Constraint(执行约束)中
	function addConstraint(node1, node2, type, len) {
		constraintArr.push([node1, node2, type, len]);
	}

	// 执行约束
	function runConstraint(node1, node2, type, len) {
		if (type == 1) { // 最大长度约束
			if (node1 == nowNode || node2 == nowNode) {
				if (node2 == nowNode) {
					let t = node2;
					node2 = node1;
					node1 = t;
				}
				if (node1.x == node2.x && node1.y == node2.y) {
					node1.x += 0.00001;
					node1.y += 0.00001;
				}
				let x2 = node1.x;
				let x3 = node2.x;
				let y2 = node1.y;
				let y3 = node2.y;
				let cslen = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
				if (cslen > len) {
					let tlen = cslen - (cslen - len) * bfb;
					let cbl = tlen / cslen;
					node2.x = node1.x + (node2.x - node1.x) * cbl;
					node2.y = node1.y + (node2.y - node1.y) * cbl;
					setPosition(node1);
					setPosition(node2);
				}
			} else {
				if (node1.x == node2.x && node1.y == node2.y) {
					node1.x += 0.00001;
					node1.y += 0.00001;
				}
				let x2 = node1.x;
				let x3 = node2.x;
				let y2 = node1.y;
				let y3 = node2.y;
				let cslen = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
				if (cslen > len) {
					let tlen = cslen - (cslen - len) * bfb;
					let cbl = tlen / cslen;
					let zx = (x2 + x3) / 2;
					let zy = (y2 + y3) / 2;
					node2.x = zx - (zx - x3) * cbl;
					node1.x = zx - (zx - x2) * cbl;
					node2.y = zy - (zy - y3) * cbl;
					node1.y = zy - (zy - y2) * cbl;
					setPosition(node1);
					setPosition(node2);
				}
			}
		} else if (type == 2) { //最小长度约束
			if (node1 == nowNode || node2 == nowNode) {
				if (node2 == nowNode) {
					let t = node2;
					node2 = node1;
					node1 = t;
				}
				if (node1.x == node2.x && node1.y == node2.y) {
					node1.x += 0.00001;
					node1.y += 0.00001;
				}
				let x2 = node1.x;
				let x3 = node2.x;
				let y2 = node1.y;
				let y3 = node2.y;
				let cslen = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
				if (cslen < len) {
					let tlen = len - (len - cslen) * bfb;
					let cbl = tlen / cslen;
					node2.x = node1.x + (node2.x - node1.x) * cbl;
					node2.y = node1.y + (node2.y - node1.y) * cbl;
					setPosition(node1);
					setPosition(node2);
				}
			} else {
				if (node1.x == node2.x && node1.y == node2.y) {
					node1.x += 0.00001;
					node1.y += 0.00001;
				}
				let x2 = node1.x;
				let x3 = node2.x;
				let y2 = node1.y;
				let y3 = node2.y;
				let cslen = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
				if (cslen < len) {
					let tlen = len - (len - cslen) * bfb;
					let cbl = tlen / cslen;
					let zx = (x2 + x3) / 2;
					let zy = (y2 + y3) / 2;
					node2.x = zx - (zx - x3) * cbl;
					node1.x = zx - (zx - x2) * cbl;
					node2.y = zy - (zy - y3) * cbl;
					node1.y = zy - (zy - y2) * cbl;
					setPosition(node1);
					setPosition(node2);
				}
			}
		} else if (type == 3) { // 边界约束
			let x2 = node1.x;
			let y2 = node1.y;
			if (x2 < leftBoundary + boundaryMinLength) {
				node1.x = node1.x + (leftBoundary + boundaryMinLength - node1.x) * bfb;
			} else if (x2 > rightBoundary - boundaryMinLength) {
				node1.x = node1.x - (node1.x - rightBoundary + boundaryMinLength) * bfb;
			}
			if (y2 - topBoundary < boundaryMinLength) {
				node1.y = node1.y + (topBoundary + boundaryMinLength - node1.y) * bfb;
			} else if (bottomBoundary - y2 < boundaryMinLength) {
				node1.y = node1.y - (node1.y - bottomBoundary + boundaryMinLength) * bfb;
			}
			setPosition(node1);
		}
	}

	// 维护约束的定时器
	let constraintTimer = setInterval(function () {
		if (!lockingNode.state) {
			for (let i = 0; i < constraintArr.length; i++) {
				let node1 = constraintArr[i][0];
				let node2 = constraintArr[i][1];
				let type = constraintArr[i][2];
				let len = constraintArr[i][3];
				runConstraint(node1, node2, type, len);
			}
		}
	}, userPerformance);

	// 维护节点间线条的定时器
	let lineTimer = setInterval(function () {
		for (let i = 0; i < setLineArr.length; i++) {
			setline(setLineArr[i]);
		}
	}, userPerformance);

	// 维护节点位置的定时器
	// setInterval(function () {
	//     for (let node of nodeSet) {
	//         setPosition(node);
	//     }
	// }, userPerformance);

	window.graphReload = function () {
		routeList.removeAllChild();
		clearInterval(constraintTimer);
		clearInterval(lineTimer);
		for (let node of nodeSet) {
			node.removeDom();
		}
		for (let line of setLineArr) {
			line.line.removeDom();
		}
		nodeSet = new Set();
		constraintArr = new Array();
		setLineArr = new Array();
		nowNodeBox.hide();
		let reader = new FileReader();
		reader.readAsText(nowFile);
		reader.onload = function () {
			console.log(1);
			data = JSON.parse(reader.result);
			start();
		};
	};
}

if (model == 'debug') {
	start();
}

// document.addEventListener('keydown', function (e) {
//     if (e.key == 'r') {
//         window.graphReload();
//     }
// });