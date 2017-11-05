class Item {
	constructor(cfg, parent) {
		console.log('xxxx');
		this.name = cfg.name;
		this.init(cfg, parent);
	}

	init(cfg, parent) {}

	getValue() {
		let v = this.element.val();;
		if (v) {
			return {
				[this.name]: v
			}
		}
	}
};

export default Item;