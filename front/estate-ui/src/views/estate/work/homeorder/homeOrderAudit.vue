<template>
  <div class="app-container">
    <WorkOrderAudit :orderType="orderType"
                    :adoptPermission="adoptPermission"
                    :rejectPermission="rejectPermission"
    ></WorkOrderAudit>

  </div>
</template>

<script>
import WorkOrderAudit from "@/components/work/workOrderAudit";
export default {
  name: "homeOrderAudit",
  components: {
    WorkOrderAudit
  },
  data() {
    return {
      // 工单类型
      orderType: "5",
      adoptPermission: "work:orderaudit:adopt",
      rejectPermission: "work:orderaudit:reject"
    };
  }
}
</script>

<style scoped>

</style>
