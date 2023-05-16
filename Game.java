package text3;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/** 游戏主体 */
public class Game {

	static Character player;
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		// 初始化
		init();

		// 主程序
		while (true) {
			menu();
		}
	}

	private static void init() {
		Equipment hat = new hat();
		Equipment cloth = new cloth();
		Equipment weapons = new weapons();
		Equipment trousers = new trousers();
		Equipment shoes = new shoes();
		Equipment necklace = new necklace();
		Equipment ring = new ring();
		EquipmentManager.backpack.add(hat);
		EquipmentManager.backpack.add(cloth);
		EquipmentManager.backpack.add(weapons);
		EquipmentManager.backpack.add(trousers);
		EquipmentManager.backpack.add(shoes);
		EquipmentManager.backpack.add(necklace);
		EquipmentManager.backpack.add(ring);
		// TODO: warehouse
		// EquipmentManager.warehouse.add(bows);
		// EquipmentManager.warehouse.add(swords);

		chooseCharacter();
	}

	private static void chooseCharacter() {

		System.out.println("选择你的职业，你只有一次机会（输入相应的序号）：");
		System.out.println("1、剑士");
		System.out.println("2、法师");
		System.out.println("3、弓箭手");

		switch (inputChoice()) {
			default:
			case 1:
				player = new Warrior();
				break;
			case 2:
				player = new Wizard();
				break;
			case 3:
				player = new Archer();
				break;
		}

		System.out.println("现在你是一个" + player.type + "\n" + " ");
		System.out.println(player.information());
	}

	private static void menu() {

		System.out.println("你想做什么呢");
		System.out.println("1:查看角色信息");
		System.out.println("2:装备");
		System.out.println("3:卸下");
		System.out.println("4:已装备的属性值");
		System.out.println("5:未装备的属性值");
		System.out.println("6:升级装备");
		System.out.println("7:挑战地牢");
		System.out.println("8:新手礼包");
		System.out.println("9:查看材料的数量");
		System.out.println("0:结束");

		switch (inputChoice()) {
			case 1:
				characterInfo();
				break;
			case 2:
				equipItem();
				break;
			case 3:
				unequipItem();
				break;
			case 4:
				equippedAttributes();
				break;
			case 5:
				unequippedAttributes();
				break;
			case 6:
				upgradeItem();
				break;
			case 7:
				challengeDungeon();
				break;
			case 8:
				beginnerGift();
				break;
			case 9:
				materialCount();
				break;
			case 0:
				System.out.println("结束!" + "\n");
				System.exit(0);
				break;
			default:
				System.out.println("你的选择似乎超出了我的范围" + "\n" + " ");
				break;
		}
	}

	private static void characterInfo() {
		System.out.println(player.information());
		EquipmentManager.showCurrentEquips();
		EquipmentManager.showBackpack();
		System.out.println("ok!" + "\n" + " ");
	}

	private static void equipItem() {
		if (EquipmentManager.nowEquips.size() >= 7) {
			System.out.println("你的装备已经满了!" + "\n" + " ");
		} else {
			System.out.println("你选择哪件呢");
			EquipmentManager.showBackpack();
			if (EquipmentManager.backpack.isEmpty())
				return;
			int rc = inputChoice();
			if (EquipmentManager.backpack.get(rc).type() == "hat" && player.hat.equals(null)
					|| EquipmentManager.backpack.get(rc).type() == "cloth" && player.cloth.equals(null)
					|| EquipmentManager.backpack.get(rc).type() == "weapons" && player.weapons.equals(null)
					|| EquipmentManager.backpack.get(rc).type() == "trousers" && player.trousers.equals(null)
					|| EquipmentManager.backpack.get(rc).type() == "necklace" && player.necklace.equals(null)
					|| EquipmentManager.backpack.get(rc).type() == "ring" && player.ring.equals(null)) {
				player.putOn(EquipmentManager.backpack.get(rc));
				EquipmentManager.nowEquips.add(EquipmentManager.backpack.get(rc));
				EquipmentManager.backpack.remove(EquipmentManager.backpack.get(rc));
				System.out.println("装备成功!" + "\n" + " ");
				System.out.println("ok!" + "\n" + " ");
			} else
				System.out.println("这个装备已经装备了哦!" + "\n" + " ");
		}
	}

	private static void unequipItem() {
		System.out.println("你要卸下哪件装备呢");
		EquipmentManager.showCurrentEquips();
		if (EquipmentManager.nowEquips.isEmpty())
			return;
		int rr = inputChoice();
		player.takeOff(EquipmentManager.nowEquips.get(rr));
		EquipmentManager.backpack.add(EquipmentManager.nowEquips.get(rr));
		EquipmentManager.nowEquips.remove(EquipmentManager.nowEquips.get(rr));
		System.out.println("卸下!" + "\n" + " ");
		System.out.println("ok!" + "\n" + " ");
	}

	private static void upgradeItem() {
		System.out.println("你要升级哪里呢");
		System.out.println("1:装备");
		System.out.println("2:背包");
		int choice3 = inputChoice();
		if (choice3 == 1) {
			System.out.println("请先卸下");
			return;
		} else if (choice3 == 2) {
			System.out.println("你要升级哪里呢");
			EquipmentManager.showBackpack();
			if (EquipmentManager.backpack.isEmpty())
				return;
			int rd = inputChoice();
			Equipment ux = EquipmentManager.backpack.get(rd);
			System.out.println("选择强化方向");
			System.out.println("1:强化度");
			System.out.println("2:品质");
			int choice4 = inputChoice();
			if (choice4 == 1) {
				if (ux.im <= 16) {
					if (player.currency >= 50) {
						ux.upIm();
						player.currency -= 50;
						System.out.println("强化成功");
					} else
						System.out.println("对不起你的钱不够哦!");
				} else {
					if (player.currency >= 100) {
						ux.upIm();
						player.currency -= 100;
						System.out.println("强化成功");
					} else
						System.out.println("对不起你的钱不够哦!");
				}
			} else if (choice4 == 2) {
				if (ux.grade <= 3) {
					if (EquipmentManager.materials.size() >= 1) {
						ux.upGr();
						EquipmentManager.materials.remove(EquipmentManager.materials.get(0));
						System.out.println("强化成功");
					} else
						System.out.println("对不起你的钱不够哦!");
				} else {
					if (EquipmentManager.materials.size() >= 2) {
						ux.upGr();
						EquipmentManager.materials.remove(EquipmentManager.materials.get(0));
						EquipmentManager.materials.remove(EquipmentManager.materials.get(0));
						System.out.println("强化成功");
					} else
						System.out.println("对不起你的钱不够哦!");
				}
			} else {
				System.out.println("错误");
				return;
			}
		} else {
			System.out.println("错误");
			return;
		}
		System.out.println("ok!" + "\n" + " ");
	}

	private static void equippedAttributes() {
		System.out.println("你要找哪件装备呢");
		EquipmentManager.showCurrentEquips();
		if (EquipmentManager.nowEquips.isEmpty())
			return;
		int ra = inputChoice();
		System.out.println(EquipmentManager.nowEquips.get(ra).toeq());
		System.out.println("ok!" + "\n" + " ");
	}

	private static void unequippedAttributes() {
		System.out.println("你要找什么道具呢");
		EquipmentManager.showBackpack();
		if (EquipmentManager.backpack.isEmpty())
			return;
		int rb = inputChoice();
		System.out.println(EquipmentManager.backpack.get(rb).toeq());
		System.out.println("ok!" + "\n" + " ");
	}

	private static void challengeDungeon() {
		new Fight(player);
		System.out.println("ok!" + "\n" + " ");
	}

	private static void beginnerGift() {
		ArrayList<Equipment> bpb = new ArrayList<Equipment>();
			Equipment hat = new hat();
		
		
			Equipment cloth = new cloth();
		
		
			Equipment weapons = new weapons();
		
		
			Equipment trousers = new trousers();
		
			Equipment shoes = new shoes();
		
			Equipment necklace = new necklace();
			
			Equipment ring = new ring();
			EquipmentManager.backpack.add(hat);
			EquipmentManager.backpack.add(cloth);
			EquipmentManager.backpack.add(weapons);
			EquipmentManager.backpack.add(trousers);
			EquipmentManager.backpack.add(shoes);
			EquipmentManager.backpack.add(necklace);
			EquipmentManager.backpack.add(ring);
	}

	private static void materialCount() {
		System.out.println("你的材料的数量: " + EquipmentManager.materials.size());
	}

	private static int inputChoice() {

		int choice;
		while (true) {
			try {
				choice = scanner.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("输入格式不正确（整数），请重新输入：");
				scanner.nextLine();
			}
		}

		return choice;
	}
}

/** 装备管理系统 */
class EquipmentManager {

	/** 当前装备 */
	static ArrayList<Equipment> nowEquips = new ArrayList<>();
	/** 背包 */
	static ArrayList<Equipment> backpack = new ArrayList<>();
	/** 仓库 */
	static ArrayList<Equipment> warehouse = new ArrayList<>();
	/** 材料库 */
	static ArrayList<Equipment> materials = new ArrayList<>();

	/**
	 * 显示当前装备
	 */
	static void showCurrentEquips() {
		if (nowEquips.isEmpty()) {
			System.out.println("你没有任何装备\n");
		} else {
			System.out.println("当前装备：\n");
			for (int i = 0; i < nowEquips.size(); i++) {
				Equipment equip = nowEquips.get(i);
				System.out.println("编号：" + i);
				System.out.println(equip.name);
			}
		}
	}

	/**
	 * 显示背包里的装备
	 */
	static void showBackpack() {
		if (backpack.isEmpty()) {
			System.out.println("你的背包空空如也\n");
		} else {
			System.out.println("背包里的装备：\n");
			for (int i = 0; i < backpack.size(); i++) {
				Equipment equip = backpack.get(i);
				System.out.println("编号：" + i);
				System.out.println(equip.name);
			}
		}
	}
}

class Fight {

	Character player;
	Scanner sc = new Scanner(System.in);

	public Fight(Character player) {
		this.player = player;
		Monster monster;

		double k = Math.random();
		if (player.level < 5 || player.level >= 5 && k < 0.6) {
			System.out.println("你遭遇了魔猪");
			monster = new Pig();
		} else if (player.level >= 5 && k < 0.9) {
			System.out.println("你遭遇了魔猫");
			monster = new Cat();
		} else {
			System.out.println("你遭遇了神秘人");
			monster = new Boss();
		}

		System.out.println("你的生命值: " + player.HP);
		System.out.println("对手的生命值: " + monster.HP);
		System.out.println("对战开始!");

		int i = 1;
		while (player.HP > 0 && monster.HP > 0) {
			System.out.println("回合 " + i);
			System.out.println("你的回合");
			System.out.println("是否使用魔法?" + "\n" + "0:yes" + "   " + "1:no");
			System.out.println("你的法力值: " + player.MP);
			int sp = sc.nextInt();
			if (sp != 0 || player.MP - 10 < 0) {
				if (sp == 0 || player.MP - 5 < 0)
					System.out.println("对不起!法力不足!");
				if (player.AGI > monster.AGI) {
					attackMonster(monster, false);
					attackPlayer(monster);
				} else {
					attackPlayer(monster);
					attackMonster(monster, false);
				}
			} else {
				System.out.println("你使用了技能!");
				player.MP -= 5;
				if (player.AGI > monster.AGI) {
					attackMonster(monster, true);
					attackPlayer(monster);
				} else {
					attackPlayer(monster);
					attackMonster(monster, true);
				}
			}
			System.out.println("对手还剩: " + monster.HP + "血");
			System.out.println("你还剩: " + player.HP + "血");
			if (player.HP > 0 && monster.HP > 0) {
				System.out.println("对战继续!");
				i += 1;
			} else {
				System.out.println("对战结束");
				return;
			}
		}
	}

	/** 计算升级所需经验 */
	private int calculateExps() {
		return player.level * 100;
	}

	/** 增加生命值 */
	public void increaseHP() {
		int HP = player.HP + new Random().nextInt(21) + 10; // 随机增加10~30点生命值
		player.HP = HP;
		System.out.println(player.name + "恢复了" + HP + "点生命值！");
	}

	/** 增加法术值 */
	public void increaseMP() {
		int MP = player.MP + new Random().nextInt(21) + 10; // 随机增加10~30点法术值
		player.MP = MP;
		System.out.println(player.name + "恢复了" + MP + "点法术值！");
	}

	/**
	 * 攻击怪物
	 * 
	 * @param monster 要攻击的怪物
	 * @param isMagic 是否为魔法攻击
	 */
	public void attackMonster(Monster monster, boolean isMagic) {
		int damage = (int) ((isMagic ? (player.MATK - monster.MADE) : (player.ATK - monster.DEF)) * Math.random());
		if (damage > 0) {
			monster.HP -= damage;
			String attackType = isMagic ? "魔法" : "物理";
			System.out.println(player.name + "对" + monster.name + "造成了" + damage + "点" + attackType + "伤害！");
			if (monster.HP <= 0) {
				System.out.println("你胜利了!");
				int exp = (int) ((new Random().nextInt(101) + 100) * player.expc); // 随机获得100~200点经验值
				System.out.println(player.name + "获得了" + exp + "点经验值！");
				player.exp += exp;
				while (exp >= calculateExps()) {
					player.exp -= calculateExps();
					player.level += 1;
				}((Pig) monster).gl();
			}
		} else {
			System.out.println(player.name + "的攻击对" + monster.name + "没有造成伤害！");
		}
	}

	/**
	 * 受到怪物的攻击
	 * 
	 * @param monster 攻击者
	 */
	public void attackPlayer(Monster monster) {
		int damage = (int) ((monster.ATK - player.DEF) * Math.random());
		if (damage > 0) {
			player.HP -= damage;
			System.out.println(monster.name + "对" + player.name + "造成了" + damage + "点物理伤害！");
			
			if (player.HP <= 0) {
				System.out.println(player.name + "被" + monster.name + "击败了！");
				System.out.println("少侠请重新来过!");
			}
		} else {
			System.out.println(monster.name + "的攻击对" + player.name + "没有造成伤害！");
		}
	}

}

class Character {

	/** 名称 */
	String name = "";
	/** 角色类型 */
	String type = "";

	/** 货币 */
	int currency = 0;
	/** 等级 */
	int level = 0;
	/** 经验 */
	int exp = 0;
	/** 经验倍率 */
	double expc = 0.0;
	/** 幸运值 */
	int lucky = 0;
	/** 品质 */
	int quality = 0;
	/** 职业 */
	int jobwork = 0;

	/** 攻击力 */
	int ATK = 0;
	/** 防御力 */
	int DEF = 0;
	/** 生命值 */
	int HP = 0;
	/** 魔力值 */
	int MP = 0;
	/** 魔法攻击 */
	int MATK = 0;
	/** 魔法防御 */
	int MADE = 0;
	/** 命中率 */
	int HIT = 0;
	/** 暴击率 */
	int CRT = 0;
	/** 闪避率 */
	int DEX = 0;
	/** 速度 */
	int AGI = 0;

	/** 防御装备值 */
	int df = 0;
	/** 生命装备值 */
	int lf = 0;
	/** 魔法装备值 */
	int ma = 0;
	/** 等级装备值 */
	int lev = 0;

	/** 头盔装备名称 */
	String hat = "";
	/** 衣服装备名称 */
	String cloth = "";
	/** 武器装备名称 */
	String weapons = "";
	/** 裤子装备名称 */
	String trousers = "";
	/** 鞋子装备名称 */
	String shoes = "";
	/** 项链装备名称 */
	String necklace = "";
	/** 戒指装备名称 */
	String ring = "";

	/** 构造函数 */
	public Character() {
		name = generateName(3);
		init();
	}

	/** 初始化 */
	public void init() {
	}

	/** 战力 */
	public int power() {
		return 0;
	}

	/** 生成随机的中文名字 */
	private static String generateName(int length) {

		if (length <= 0)
			length = 3;

		String name = "";

		for (int i = 0; i < length; i++) {

			String charStr = null;
			Random random = new Random();

			// 获取高位值，范围在176~215之间
			int highPos = 176 + Math.abs(random.nextInt(39));
			// 获取低位值，范围在161~254之间
			int lowPos = 161 + Math.abs(random.nextInt(93));

			byte[] b = new byte[2];
			b[0] = (Integer.valueOf(highPos).byteValue());
			b[1] = (Integer.valueOf(lowPos).byteValue());

			try {
				charStr = new String(b, "GBK"); // 转成中文
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}

			name += charStr;
		}

		return name;
	}

	public String information() {
		return "个人面版" + "\n" + type + "名字: " + name + "\n" + "等级: " + level + "\n" + "攻击力: " + ATK + "\n" + "生命值: " + HP
				+ "\n" + "防御力: " + DEF + "\n" + "魔力值: " + MP + "魔法攻击: " + MATK + "\n" + "魔法防御: " + MADE + "\n" + "命中率: "
				+ HIT + "\n" + "闪避率: " + DEX + "\n" + "速度: " + AGI + "\n" + "战力" + power();
	}

	public void updateEquipment(int n, String ty) {
		if (ty == "att")
			ATK = ATK + n;
		else if (ty == "def")
			df = df + n;
		else if (ty == "life")
			lf = lf + n;
		else if (ty == "mana")
			ma = ma + n;
		else if (ty == "lev") {
			if (lev + n <= 10)
				lev = lev + n;
			else
				lev = 10;
		}
	}

	public void putOn(Equipment a) {
		String c = a.addWhat();
		int d = a.addx();
		updateEquipment(d, c);
	}

	public void takeOff(Equipment a) {
		String c = a.addWhat();
		int d = a.addx() * (-1);
		updateEquipment(d, c);
	}

	public double attackSkill() {
		return (double) ATK;
	}
}

class Warrior extends Character {

	
	public void init() {
		super.init();

		type = "剑士";
		expc = 1.0;
		jobwork = 1;

		ATK = 70;
		DEF = 100;
		MADE = 100;
		MATK = 0;
		MP = 0;
		HP = 100;
		DEX = 0;
		AGI = 30;
		HIT = 100;
	}

	
	public int power() {
		return (int) ((ATK + MATK) * 0.8 + (DEF + MADE) * 0.2 + (MP + HP) * 0.5);
	}

	
	public double attackSkill() {
		System.out.println("I am invincible!");
		return (double) ATK + df * 0.5;
	}
}

class Wizard extends Character {

	
	public void init() {
		super.init();

		type = "法师";
		expc = 1.0;
		jobwork = 3;

		ATK = 30;
		DEF = 30;
		MADE = 30;
		MATK = 100;
		MP = 100;
		HP = 50;
		DEX = 0;
		AGI = 20;
		HIT = 80;
	}

	
	public int power() {
		return (int) ((ATK + MATK) * 0.8 + (DEF + MADE) * 0.2 + (MP + HP) * 0.5);
	}

	
	public double attackSkill() {
		System.out.println("Justice from heaven!");
		return (double) ATK + ma * 0.5;
	}
}

class Archer extends Character {

	
	public void init() {
		super.init();

		type = "弓箭手";
		expc = 1.0;
		jobwork = 2;

		ATK = 100;
		DEF = 30;
		MADE = 30;
		MATK = 50;
		MP = 50;
		HP = 50;
		DEX = 10;
		AGI = 50;
		HIT = 60;
	}

	
	public int power() {
		return (int) ((ATK + MATK) * 0.8 + (DEF + MADE) * 0.2 + (MP + HP) * 0.5);
	}

	
	public double attackSkill() {
		System.out.println("Arrow rain!");
		return (double) ATK * 1.7;
	}
}

class Monster {

	String name;
	Equipment mat;

	/** 攻击力 */
	int ATK = 0;
	/** 防御力 */
	int DEF = 0;
	/** 生命值 */
	int HP = 0;
	/** 魔力值 */
	int MP = 0;
	/** 魔法攻击 */
	int MATK = 0;
	/** 魔法防御 */
	int MADE = 0;
	/** 命中率 */
	int HIT = 0;
	/** 暴击率 */
	int CRT = 0;
	/** 闪避率 */
	int DEX = 0;
	/** 速度 */
	int AGI = 0;

	/** 构造函数 */
	public Monster() {
		init();
	}

	/** 初始化 */
	public void init() {
	}

	Equipment drop() {
		return null;
	}
}

class Pig extends Monster {
	public Equipment dlw() {
		int a = (int) (Math.random() * 7);
		if (a == 0) {
			Equipment hat = new hat();
			return hat;
		}
		if (a == 1) {
			Equipment cloth = new cloth();
			return cloth;
		}
		if (a == 2) {
			Equipment weapons = new weapons();
			return weapons;
		}
		if (a == 3) {
			Equipment trousers = new trousers();
			return trousers;
		}
		if (a == 4) {
			Equipment shoes = new shoes();
			return shoes;
		}
		if (a == 5) {
			Equipment necklace = new necklace();
			return necklace;
		}
		if (a == 6) {
			Equipment ring = new ring();
			return ring;
		}
		Equipment hat=new hat();
		return hat;
	}

	static ArrayList<Equipment> bpb = new ArrayList<Equipment>();
	public void name() {
		name = "pig";
	}
	public void def() {
		DEF = 30;
	}

	public void att() {
		ATK = 15;
	}

	

	public void skill() {
		DEF = DEF * 2;
	}

	public double gl() {
		bpb.add(dlw());
		EquipmentManager.materials.add(mat);
		System.out.println("你有了新的装备!");
		System.out.println("你有了新的材料!");
		return 0.8;
	}

	
	public void init() {
		DEX = 20;
		AGI = 40;
		MP = 0;
		HP = 100;
		MADE = 50;
		MATK = 50;
		DEF = 50;
		ATK = 50;
		HIT = 50;
	}
}

class Cat extends Monster {
	public Equipment dlw() {
		int a = (int) (Math.random() * 7);
		if (a == 0) {
			Equipment hat = new hat();
			return hat;
		}
		if (a == 1) {
			Equipment cloth = new cloth();
			return cloth;
		}
		if (a == 2) {
			Equipment weapons = new weapons();
			return weapons;
		}
		if (a == 3) {
			Equipment trousers = new trousers();
			return trousers;
		}
		if (a == 4) {
			Equipment shoes = new shoes();
			return shoes;
		}
		if (a == 5) {
			Equipment necklace = new necklace();
			return necklace;
		}
		if (a == 6) {
			Equipment ring = new ring();
			return ring;
		}
		Equipment hat=new hat();
		return hat;
	}

	static ArrayList<Equipment> bpb = new ArrayList<Equipment>();
	public void name() {
		name = "cat";
	}
	public void def() {
		DEF = 15;
	}

	public void att() {
		ATK = 30;
	}



	public void skill() {
		ATK = ATK * 3;
	}

	public double gl() {
		bpb.add(dlw());
		EquipmentManager.materials.add(mat);
		System.out.println("你有了新的装备!");
		System.out.println("你有了新的材料!");
		return 0.8;
	}

	
	public void init() {
		DEX = 30;
		AGI = 50;
		MP = 30;
		HP = 150;
		MADE = 70;
		MATK = 70;
		DEF = 30;
		ATK = 50;
		HIT = 80;
	}
}

class Boss extends Monster {
	Equipment armors;
	int Gold = 200;

	public Equipment dlw() {
		int a = (int) (Math.random() * 7);
		if (a == 0) {
			Equipment hat = new hat();
			return hat;
		}
		if (a == 1) {
			Equipment cloth = new cloth();
			return cloth;
		}
		if (a == 2) {
			Equipment weapons = new weapons();
			return weapons;
		}
		if (a == 3) {
			Equipment trousers = new trousers();
			return trousers;
		}
		if (a == 4) {
			Equipment shoes = new shoes();
			return shoes;
		}
		if (a == 5) {
			Equipment necklace = new necklace();
			return necklace;
		}
		if (a == 6) {
			Equipment ring = new ring();
			return ring;
		}Equipment hat=new hat();
		return hat;
		
	}

	static ArrayList<Equipment> bpb = new ArrayList<Equipment>();
	public void name() {
		name = "boss";
	}
	public void skill() {
		DEF = DEF * 2;
		ATK = ATK * 2;
	}

	public double gl() {
		bpb.add(armors);
		EquipmentManager.materials.add(mat);
		System.out.println("你有了新的装备!");
		System.out.println("你有了新的材料!");
		return 0.5;
	}

	
	public void init() {
		DEX = 100;
		AGI = 80;
		MP = 100;
		HP = 300;
		MADE = 100;
		MATK = 100;
		DEF = 100;
		ATK = 90;
		HIT = 100;
	}
}

class Mysteryman extends Monster {
	public void skill() {
		System.out.println("你的敌人已经暴走 !");
		System.out.println("丧失一些自己的防御 !");
		System.out.println("并将转换为攻击力!");
		System.out.println("请尽可能打败它!");
		ATK = ATK * 3;
		DEF = (int) (DEF * 0.7);
		MADE = (int) (MADE * 0.5);
	}

	public void honor() {
		System.out.println("你已经打败了神秘人!" + "/n" + "解放了这个世界");
		System.out.println("祝贺完成游戏!");
	}

	public void name() {
		name = "Mysteryman";
	}
	public void init() {
		DEX = 200;
		AGI = 100;
		MP = 300;
		HP = 500;
		MADE = 200;
		MATK = 200;
		DEF = 200;
		ATK = 120;
		HIT = 300;
	}
}

class Equipment {

	String name;
	int quality, power, jobwork, lucky;
	double expc;
	int ATK, DEF, HP, MP, MATK, MADE;
	int HIT, CRT, DEX, AGI;
	int im, grade;

	public void quality() {
		int a = (int) (Math.random() * 1000000);
		while (a > 0) {
			quality = quality + 1;
			a = a / 10;
		}
	}

	public String addWhat() {
		return null;
	}

	public int addx() {
		return 0;
	}

	public void upIm() {
	}

	public void upGr() {
	}

	public String toeq() {
		return " ";
	}

	public String toName() {
		return " ";
	}

	public String type() {
		return null;
	}
}

class hat extends Equipment {

	public String type() {
		return "hat";
	}

	public void HP() {
		if (quality == 1)
			;
		HP = (int) (Math.random() * 11 + 20);
		if (quality == 2)
			;
		HP = (int) (Math.random() * 21 + 40);
		if (quality == 3)
			;
		HP = (int) (Math.random() * 31 + 60);
		if (quality == 4)
			;
		HP = (int) (Math.random() * 41 + 80);
		if (quality == 5)
			;
		HP = (int) (Math.random() * 51 + 100);
		if (quality == 6)
			;
		HP = (int) (Math.random() * 61 + 120);
	}

	
	public void HIT() {
		if (quality == 1)
			;
		HIT = (int) (Math.random() * 7);
		if (quality == 2)
			;
		HIT = (int) (Math.random() * 11);
		if (quality == 3)
			;
		HIT = (int) (Math.random() * 15);
		if (quality == 4)
			;
		HIT = (int) (Math.random() * 21);
		if (quality == 5)
			;
		HIT = (int) (Math.random() * 25);
		if (quality == 6)
			;
		HIT = (int) (Math.random() * 31);
	}

	
	public void expc() {
		int a = (int) (Math.random() * quality + 0.1);
		if (a > 1)
			;
		expc = ((int) Math.random() * 10) / 10;
	}

	
	public void MP() {
		if (quality == 1)
			;
		MP = (int) (Math.random() * 21) - 10;
		if (quality == 2)
			;
		MP = (int) (Math.random() * 41) - 20;
		if (quality == 3)
			;
		MP = (int) (Math.random() * 61) - 30;
		if (quality == 4)
			;
		MP = (int) (Math.random() * 81) - 40;
		if (quality == 5)
			;
		MP = (int) (Math.random() * 101) - 50;
		if (quality == 6)
			;
		MP = (int) (Math.random() * 121) - 60;
	}

	
	public void jobwork() {
		jobwork = (int) Math.random() * 3 + 1;
	}
}

class cloth extends Equipment {

	
	public void AGI() {
		if (quality == 1)
			;
		AGI = (int) (Math.random() * 11) - 5 - 5;
		if (quality == 2)
			;
		AGI = (int) (Math.random() * 21) - 15 - 5;
		if (quality == 3)
			;
		AGI = (int) (Math.random() * 31) - 25 - 5;
		if (quality == 4)
			;
		AGI = (int) (Math.random() * 41) - 20 - 5;
		if (quality == 5)
			;
		AGI = (int) (Math.random() * 51) - 25 - 5;
		if (quality == 6)
			;
		AGI = (int) (Math.random() * 61) - 30 - 5;
	}

	
	public void DEF() {
		if (quality == 1)
			;
		DEF = (int) (Math.random() * 21) - 10;
		if (quality == 2)
			;
		DEF = (int) (Math.random() * 41) - 20;
		if (quality == 3)
			;
		DEF = (int) (Math.random() * 61) - 30;
		if (quality == 4)
			;
		DEF = (int) (Math.random() * 81) - 40;
		if (quality == 5)
			;
		DEF = (int) (Math.random() * 101) - 50;
		if (quality == 6)
			;
		DEF = (int) (Math.random() * 121) - 60;
	}

	
	public void MADE() {
		if (quality == 1)
			;
		MADE = (int) (Math.random() * 11) - 5;
		if (quality == 2)
			;
		MADE = (int) (Math.random() * 21) - 10;
		if (quality == 3)
			;
		MADE = (int) (Math.random() * 31) - 15;
		if (quality == 4)
			;
		MADE = (int) (Math.random() * 41) - 20;
		if (quality == 5)
			;
		MADE = (int) (Math.random() * 51) - 25;
		if (quality == 6)
			;
		MADE = (int) (Math.random() * 61) - 30;
	}

	
	public void HP() {
		if (quality == 1)
			;
		HP = (int) (Math.random() * 21 + 40);
		if (quality == 2)
			;
		HP = (int) (Math.random() * 41 + 80);
		if (quality == 3)
			;
		HP = (int) (Math.random() * 61 + 120);
		if (quality == 4)
			;
		HP = (int) (Math.random() * 81 + 160);
		if (quality == 5)
			;
		HP = (int) (Math.random() * 101 + 200);
		if (quality == 6)
			;
		HP = (int) (Math.random() * 121 + 240);
	}

	public void jobwork() {
		jobwork = (int) Math.random() * 3 + 1;
	}

	public String type() {
		return "cloth";
	}
}

class weapons extends Equipment {

	
	public void AGI() {
		if (quality == 1)
			;
		AGI = (int) (Math.random() * 21) - 10;
		if (quality == 2)
			;
		AGI = (int) (Math.random() * 41) - 20;
		if (quality == 3)
			;
		AGI = (int) (Math.random() * 61) - 30;
		if (quality == 4)
			;
		AGI = (int) (Math.random() * 81) - 40;
		if (quality == 5)
			;
		AGI = (int) (Math.random() * 101) - 50;
		if (quality == 6)
			;
		AGI = (int) (Math.random() * 121) - 60;
	}

	
	public void MATK() {
		if (quality == 1)
			;
		MATK = (int) (Math.random() * 21) - 10;
		if (quality == 2)
			;
		MATK = (int) (Math.random() * 41) - 20;
		if (quality == 3)
			;
		MATK = (int) (Math.random() * 61) - 30;
		if (quality == 4)
			;
		MATK = (int) (Math.random() * 81) - 40;
		if (quality == 5)
			;
		MATK = (int) (Math.random() * 101) - 50;
		if (quality == 6)
			;
		MATK = (int) (Math.random() * 121) - 60;
	}

	public String type() {
		return "weapons";
	}

	public void ATK() {
		if (quality == 1)
			;
		ATK = (int) (Math.random() * 21) - 10;
		if (quality == 2)
			;
		ATK = (int) (Math.random() * 41) - 20;
		if (quality == 3)
			;
		ATK = (int) (Math.random() * 61) - 30;
		if (quality == 4)
			;
		ATK = (int) (Math.random() * 81) - 40;
		if (quality == 5)
			;
		ATK = (int) (Math.random() * 101) - 50;
		if (quality == 6)
			;
		ATK = (int) (Math.random() * 121) - 60;
	}

	public void jobwork() {
		jobwork = (int) Math.random() * 3 + 1;
	}
}

class trousers extends Equipment {

	public String type() {
		return "trousers";
	}

	
	public void AGI() {
		if (quality == 1)
			;
		AGI = (int) (Math.random() * 11) - 5;
		if (quality == 2)
			;
		AGI = (int) (Math.random() * 21) - 10;
		if (quality == 3)
			;
		AGI = (int) (Math.random() * 31) - 15;
		if (quality == 4)
			;
		AGI = (int) (Math.random() * 41) - 20;
		if (quality == 5)
			;
		AGI = (int) (Math.random() * 51) - 25;
		if (quality == 6)
			;
		AGI = (int) (Math.random() * 61) - 30;
	}

	
	public void DEF() {
		if (quality == 1)
			;
		DEF = (int) (Math.random() * 11) - 5;
		if (quality == 2)
			;
		DEF = (int) (Math.random() * 21) - 10;
		if (quality == 3)
			;
		DEF = (int) (Math.random() * 31) - 15;
		if (quality == 4)
			;
		DEF = (int) (Math.random() * 41) - 20;
		if (quality == 5)
			;
		DEF = (int) (Math.random() * 51) - 25;
		if (quality == 6)
			;
		DEF = (int) (Math.random() * 61) - 30;
	}

	
	public void MADE() {
		if (quality == 1)
			;
		MADE = (int) (Math.random() * 21) - 10;
		if (quality == 2)
			;
		MADE = (int) (Math.random() * 41) - 20;
		if (quality == 3)
			;
		MADE = (int) (Math.random() * 61) - 30;
		if (quality == 4)
			;
		MADE = (int) (Math.random() * 81) - 40;
		if (quality == 5)
			;
		MADE = (int) (Math.random() * 101) - 50;
		if (quality == 6)
			;
		MADE = (int) (Math.random() * 121) - 60;
	}

	
	public void HP() {
		if (quality == 1)
			HP = (int) (Math.random() * 11 + 20);
		if (quality == 2)
			;
		HP = (int) (Math.random() * 21 + 40);
		if (quality == 3)
			;
		HP = (int) (Math.random() * 31 + 60);
		if (quality == 4)
			;
		HP = (int) (Math.random() * 41 + 80);
		if (quality == 5)
			;
		HP = (int) (Math.random() * 51 + 100);
		if (quality == 6)
			;
		HP = (int) (Math.random() * 61 + 120);
	}

	public void jobwork() {
		jobwork = (int) Math.random() * 3 + 1;
	}
}

class shoes extends Equipment {

	public String type() {
		return "shoes";
	}

	
	public void DEX() {
		if (quality == 1)
			;
		DEX = (int) (Math.random() * 11) - 5;
		if (quality == 2)
			;
		DEX = (int) (Math.random() * 21) - 10;
		if (quality == 3)
			;
		DEX = (int) (Math.random() * 31) - 15;
		if (quality == 4)
			;
		DEX = (int) (Math.random() * 41) - 20;
		if (quality == 5)
			;
		DEX = (int) (Math.random() * 51) - 25;
		if (quality == 6)
			;
		DEX = (int) (Math.random() * 61) - 30;
	}

	
	public void AGI() {
		if (quality == 1)
			;
		AGI = (int) (Math.random() * 21) - 10;
		if (quality == 2)
			;
		AGI = (int) (Math.random() * 41) - 20;
		if (quality == 3)
			;
		AGI = (int) (Math.random() * 61) - 30;
		if (quality == 4)
			;
		AGI = (int) (Math.random() * 81) - 40;
		if (quality == 5)
			;
		AGI = (int) (Math.random() * 101) - 50;
		if (quality == 6)
			;
		AGI = (int) (Math.random() * 121) - 60;
	}

	public void jobwork() {
		jobwork = (int) Math.random() * 3 + 1;
	}

	
	public void lucky() {
		lucky = (int) (Math.random() * 10 - 5);
	}
}

class necklace extends Equipment {

	public String type() {
		return "necklace";
	}

	
	public void expc() {
		int a = (int) (Math.random() * quality + 0.1);
		if (a > 1)
			;
		expc = ((int) Math.random() * 10) / 10 * 2;
	}

	
	public void HIT() {
		if (quality == 1)
			;
		HIT = (int) (Math.random() * 7) - 3;
		if (quality == 2)
			;
		HIT = (int) (Math.random() * 11) - 5;
		if (quality == 3)
			;
		HIT = (int) (Math.random() * 15) - 7;
		if (quality == 4)
			;
		HIT = (int) (Math.random() * 21) - 10;
		if (quality == 5)
			;
		HIT = (int) (Math.random() * 25) - 12;
		if (quality == 6)
			;
		HIT = (int) (Math.random() * 31) - 15;
	}

	public void jobwork() {
		jobwork = (int) Math.random() * 3 + 1;
	}

	
	public void lucky() {
		lucky = (int) (Math.random() * 10 - 5);
	}
}

class ring extends Equipment {

	public String type() {
		return "ring";
	}

	
	public void DEX() {
		if (quality == 1)
			;
		DEX = (int) (Math.random() * 21) - 10;
		if (quality == 2)
			;
		DEX = (int) (Math.random() * 41) - 20;
		if (quality == 3)
			;
		DEX = (int) (Math.random() * 61) - 30;
		if (quality == 4)
			;
		DEX = (int) (Math.random() * 81) - 40;
		if (quality == 5)
			;
		DEX = (int) (Math.random() * 101) - 50;
		if (quality == 6)
			;
		DEX = (int) (Math.random() * 121) - 60;
	}

	
	public void HP() {
		if (quality == 1)
			;
		HP = (int) (Math.random() * 11 + 20);
		if (quality == 2)
			;
		HP = (int) (Math.random() * 21 + 40);
		if (quality == 3)
			;
		HP = (int) (Math.random() * 31 + 60);
		if (quality == 4)
			;
		HP = (int) (Math.random() * 41 + 80);
		if (quality == 5)
			;
		HP = (int) (Math.random() * 51 + 100);
		if (quality == 6)
			;
		HP = (int) (Math.random() * 61 + 120);
	}

	
	public void MP() {
		if (quality == 1)
			;
		MP = (int) (Math.random() * 11 + 20);
		if (quality == 2)
			;
		MP = (int) (Math.random() * 21 + 40);
		if (quality == 3)
			;
		MP = (int) (Math.random() * 31 + 60);
		if (quality == 4)
			;
		MP = (int) (Math.random() * 41 + 80);
		if (quality == 5)
			;
		MP = (int) (Math.random() * 51 + 100);
		if (quality == 6)
			;
		MP = (int) (Math.random() * 61 + 120);
	}

	public void expc() {
		int a = (int) (Math.random() * quality + 0.1);
		if (a > 1)
			;
		expc = ((int) Math.random() * 10) / 10;
	}

	public void jobwork() {
		jobwork = (int) Math.random() * 3 + 1;
	}
}
