# 幻想人形演舞攻略Wiki 人形個別ページ生成器
## 使い方
resourceディレクトリに決められた書式のJSONファイルを置いて
sbt run
するとwikitextディレクトリにwiki記法のファイルが生成される。

## 仕様
決められた書式のJSONを記述して実行すると、
テンプレートに従ったwikiソースが生成される。
テンプレートURL:
http://wikiwiki.jp/enbu-ap/?%A5%C6%A5%F3%A5%D7%A5%EC%A1%BC%A5%C8%C5%F9%2F%BF%CD%B7%C1%B8%C4%CA%CC%A5%DA%A1%BC%A5%B8%B5%C4%CF%C0%2Ftemp

## JSONの見本
resourceディレクトリ以下に見本がある

skill-list.csvに定義されていない技はエラーになるため
skill-list.csvの更新も必要

## 既知の問題点
* skill-list.csvにないスキルの扱い

## 外部リンク
幻想人形演舞-攻略-wiki
http://wikiwiki.jp/tensi-gj-ms/

幻想人形演舞-ユメノカケラ-攻略wiki
http://wikiwiki.jp/enbu-ap/
