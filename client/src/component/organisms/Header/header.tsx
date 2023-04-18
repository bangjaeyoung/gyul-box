import Modal from "@/component/templates/modal/Modal";
import Image from "next/image";
import gyulLogo from "../../../../public/icon/gyulLogo.png";
import styles from "../Header/Header.module.scss";
type Props = {};

export default function Header({}: Props) {
  return (
    <header className={styles.wrapper}>
      <div className={styles.logoWrapper}>
        <div className={styles.imgDiv}>
          <Image src={gyulLogo} alt="귤 로고" width={75} height={78} />
        </div>
      </div>
      <div className={styles.ulWrapper}>
        <ul className={styles.ul}>
          <li className={styles.li}>리뷰</li>
          <li className={styles.li}>게시판</li>
          <li className={styles.li}>지도</li>
        </ul>
      </div>

      <Modal isOpen={false} />
    </header>
  );
}
