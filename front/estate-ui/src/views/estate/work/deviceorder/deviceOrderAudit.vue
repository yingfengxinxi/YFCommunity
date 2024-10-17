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
  components: {
    WorkOrderAudit
  },
  data() {
    return {
      // 工单类型
      orderType: "6",
      adoptPermission: "work:deviceOrder:adopt",
      rejectPermission: "work:deviceOrder:reject"
    };
  }
};
</script>
<style scoped>
</style>
